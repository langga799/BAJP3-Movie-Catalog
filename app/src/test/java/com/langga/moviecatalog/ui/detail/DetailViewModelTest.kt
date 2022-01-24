package com.langga.moviecatalog.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.utils.DataLocal
import com.langga.moviecatalog.vo.Resource
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
    private val movieId = dataLocalMovie.id
    private val dataLocalTvShow = DataLocal.generateLocalTvShow()[0]
    private val tvShowId = dataLocalTvShow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<TvShowEntity>>

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(movieTvRepository)
        detailViewModel.getDataDetailMovies(movieId)
        detailViewModel.getDetailTvShows(tvShowId)
    }

    @Test
    fun getDataDetailMovies() {
        val resourceMovie = Resource.success(dataLocalMovie)
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = resourceMovie

        `when`(movieTvRepository.getMovieById(movieId)).thenReturn(movie)
        detailViewModel.dataDetailMovie.observeForever(movieObserver)
        verify(movieObserver).onChanged(resourceMovie)
    }

    @Test
    fun getDataDetailTvShows() {
        val resourceTvShow = Resource.success(dataLocalTvShow)
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = resourceTvShow

        `when`(movieTvRepository.getTvShowById(tvShowId)).thenReturn(tvShow)
        detailViewModel.dataDetailTvShow.observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(resourceTvShow)
    }

}