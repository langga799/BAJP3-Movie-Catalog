package com.langga.moviecatalog.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    private lateinit var favoriteViewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observerMovie: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var observerTvShow: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var pagedMovie: PagedList<MovieEntity>

    @Mock
    private lateinit var pagedTvShow: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        favoriteViewModel = FavoriteViewModel(movieTvRepository)
    }

    @Test
    fun getFavoriteMovie() {
        val localMovie = pagedMovie
        `when`(localMovie.size).thenReturn(0)
        val movie = MutableLiveData<PagedList<MovieEntity>>()
        movie.value = localMovie

        `when`(movieTvRepository.getFavoriteMovie()).thenReturn(movie)
        val movieEntity = favoriteViewModel.getFavoriteMovie().value
        verify(movieTvRepository).getFavoriteMovie()
        assertNotNull(movieEntity)
        assertEquals(0, movieEntity?.size)

        favoriteViewModel.getFavoriteMovie().observeForever(observerMovie)
        verify(observerMovie).onChanged(localMovie)
    }

    @Test
    fun getFavoriteTvShow() {
        val localTvShow = pagedTvShow
        `when`(localTvShow.size).thenReturn(0)
        val tvShow = MutableLiveData<PagedList<TvShowEntity>>()
        tvShow.value = localTvShow

        `when`(movieTvRepository.getFavoriteTvShow()).thenReturn(tvShow)
        val tvShowEntity = favoriteViewModel.getFavoriteTvShow().value
        verify(movieTvRepository).getFavoriteTvShow()
        assertNotNull(tvShowEntity)
        assertEquals(0, tvShowEntity?.size)

        favoriteViewModel.getFavoriteTvShow().observeForever(observerTvShow)
        verify(observerTvShow).onChanged(localTvShow)
    }
}