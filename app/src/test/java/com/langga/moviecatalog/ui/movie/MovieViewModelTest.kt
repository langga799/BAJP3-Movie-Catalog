package com.langga.moviecatalog.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
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
class MovieViewModelTest {

    private lateinit var movieViewModel: MovieViewModel
    private val sort = SortUtils.RATING

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var pagedMovie: PagedList<MovieEntity>

    @Before
    fun setUp() {
        movieViewModel = MovieViewModel(movieTvRepository)
    }

    @Test
    fun getMovies() {
        val dataLocalMovie = Resource.success(pagedMovie)
        `when`(dataLocalMovie.data?.size).thenReturn(0)
        val movie = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movie.value = dataLocalMovie

        `when`(movieTvRepository.getAllMovies(sort)).thenReturn(movie)
        val movieEntity = movieViewModel.getMovies(sort).value?.data
        verify(movieTvRepository).getAllMovies(sort)
        assertNotNull(movieEntity)
        assertEquals(0, movieEntity?.size)

        movieViewModel.getMovies(sort).observeForever(observer)
        verify(observer).onChanged(dataLocalMovie)
    }
}