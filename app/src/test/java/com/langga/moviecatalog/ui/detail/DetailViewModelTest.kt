package com.langga.moviecatalog.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.utils.DataLocal
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var detailViewModel: DetailViewModel
    private val dataLocalMovie = DataLocal.generateLocalMovies()[0]
    private val movieId = dataLocalMovie.id.toString()
    private val dataLocalTvShow = DataLocal.generateLocalTvShow()[0]
    private val tvShowId = dataLocalTvShow.id.toString()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var movieObserver: Observer<MovieEntity>

    @Mock
    private lateinit var tvShowObserver: Observer<TvShowEntity>

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(movieTvRepository)
    }

    @Test
    fun getDataDetailMovies() {
        val movie = MutableLiveData<MovieEntity>()
        movie.value = dataLocalMovie

        `when`(movieTvRepository.getMovieById(movieId)).thenReturn(movie)
        val movieEntity = detailViewModel.getDataDetailMovies(movieId).value as MovieEntity
        verify(movieTvRepository).getMovieById(movieId)

        assertEquals(dataLocalMovie.id, movieEntity.id)
        assertEquals(dataLocalMovie.originalTitle, movieEntity.originalTitle)
        assertEquals(dataLocalMovie.popularity.toString(), movieEntity.popularity.toString())
        assertEquals(dataLocalMovie.releaseDate, movieEntity.releaseDate)
        assertEquals(dataLocalMovie.voteAverage.toString(), movieEntity.voteAverage.toString())
        assertEquals(dataLocalMovie.overview, movieEntity.overview)
        assertEquals(dataLocalMovie.posterPath, movieEntity.posterPath)
        assertEquals(dataLocalMovie.backDropPath, movieEntity.backDropPath)
        assertEquals(dataLocalMovie.originalLanguage, movieEntity.originalLanguage)
        assertEquals(dataLocalMovie.voteCount, movieEntity.voteCount)

        detailViewModel.getDataDetailMovies(movieId).observeForever(movieObserver)
        verify(movieObserver).onChanged(dataLocalMovie)

    }

    @Test
    fun getDetailTvShows() {
        val tvShow = MutableLiveData<TvShowEntity>()
        tvShow.value = dataLocalTvShow

        `when`(movieTvRepository.getTvShowById(tvShowId)).thenReturn(tvShow)
        val tvShowEntity = detailViewModel.getDetailTvShows(tvShowId).value as TvShowEntity
        verify(movieTvRepository).getTvShowById(tvShowId)

        assertEquals(dataLocalTvShow.id, tvShowEntity.id)
        assertEquals(dataLocalTvShow.originalName, tvShowEntity.originalName)
        assertEquals(dataLocalTvShow.popularity.toString(), tvShowEntity.popularity.toString())
        assertEquals(dataLocalTvShow.firstAirDate, tvShowEntity.firstAirDate)
        assertEquals(dataLocalTvShow.voteAverage.toString(), tvShowEntity.voteAverage.toString())
        assertEquals(dataLocalTvShow.overview, tvShowEntity.overview)
        assertEquals(dataLocalTvShow.posterPath, tvShowEntity.posterPath)
        assertEquals(dataLocalTvShow.backDropPath, tvShowEntity.backDropPath)
        assertEquals(dataLocalTvShow.originalLanguage, tvShowEntity.originalLanguage)
        assertEquals(dataLocalTvShow.voteCount, tvShowEntity.voteCount)

        detailViewModel.getDetailTvShows(tvShowId).observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dataLocalTvShow)
    }


}