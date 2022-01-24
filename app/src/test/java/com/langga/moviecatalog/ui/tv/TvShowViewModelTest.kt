package com.langga.moviecatalog.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.utils.DataLocal
import com.langga.moviecatalog.utils.SortUtils
import com.langga.moviecatalog.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    private lateinit var tvShowViewModel: TvShowViewModel
    private val sort = SortUtils.RATING

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowEntity>>>

    @Mock
    private lateinit var pagedTvShow: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        tvShowViewModel = TvShowViewModel(movieTvRepository)
    }

    @Test
    fun getTvShow() {
        val dataLocalTvShow = Resource.success(pagedTvShow)
        `when`(dataLocalTvShow.data?.size).thenReturn(0)
        val tvShow = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        tvShow.value = dataLocalTvShow

        `when`(movieTvRepository.getAllTvShows(sort)).thenReturn(tvShow)
        val tvShowEntity = tvShowViewModel.getTvShow(sort).value?.data
        verify(movieTvRepository).getAllTvShows(sort)
        assertNotNull(tvShowEntity)
        assertEquals(0, tvShowEntity?.size)

        tvShowViewModel.getTvShow(sort).observeForever(observer)
        verify(observer).onChanged(dataLocalTvShow)
    }
}