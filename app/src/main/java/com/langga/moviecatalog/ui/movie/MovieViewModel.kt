package com.langga.moviecatalog.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.local.entity.MovieEntity

class MovieViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    fun getMovies(): LiveData<List<MovieEntity>> = movieTvRepository.getAllMovies()

}