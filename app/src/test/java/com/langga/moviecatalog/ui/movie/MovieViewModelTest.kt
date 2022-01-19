package com.langga.moviecatalog.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
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
class MovieViewModelTest {

    private lateinit var movieViewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<List<MovieEntity>>

    @Before
    fun setUp() {
        movieViewModel = MovieViewModel(movieTvRepository)
    }

    @Test
    fun getMovies() {
        val dataLocalMovie = DataLocal.generateLocalMovies()
        val movie = MutableLiveData<List<MovieEntity>>()
        movie.value = dataLocalMovie

        `when`(movieTvRepository.getAllMovies()).thenReturn(movie)
        val dataMovies = movieViewModel.getMovies().value
        assertNotNull(dataMovies)
        assertEquals(1, dataMovies?.size)

        movieViewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dataLocalMovie)
    }
}