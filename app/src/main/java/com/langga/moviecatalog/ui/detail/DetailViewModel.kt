package com.langga.moviecatalog.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity

class DetailViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    fun getDataDetailMovies(idMovie: String): LiveData<MovieEntity> =
        movieTvRepository.getMovieById(idMovie)

    fun getDetailTvShows(idTvShow: String): LiveData<TvShowEntity> =
        movieTvRepository.getTvShowById(idTvShow)


}