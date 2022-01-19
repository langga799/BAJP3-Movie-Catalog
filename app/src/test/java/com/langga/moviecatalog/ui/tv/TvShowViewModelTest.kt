package com.langga.moviecatalog.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.utils.DataLocal
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

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<List<TvShowEntity>>

    @Before
    fun setUp() {
        tvShowViewModel = TvShowViewModel(movieTvRepository)
    }

    @Test
    fun getTvShow() {
        val dataLocalTvShow = DataLocal.generateLocalTvShow()
        val tvShow = MutableLiveData<List<TvShowEntity>>()
        tvShow.value = dataLocalTvShow

        `when`(movieTvRepository.getAllTvShows()).thenReturn(tvShow)
        val dataTvShow = tvShowViewModel.getTvShow().value
        assertNotNull(dataTvShow)
        assertEquals(1, dataTvShow?.size)

        tvShowViewModel.getTvShow().observeForever(observer)
        verify(observer).onChanged(dataLocalTvShow)
    }
}