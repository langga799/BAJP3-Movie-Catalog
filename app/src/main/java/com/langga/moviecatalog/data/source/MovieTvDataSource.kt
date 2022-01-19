package com.langga.moviecatalog.data.source

import androidx.lifecycle.LiveData
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity

interface MovieTvDataSource {

    fun getAllMovies(): LiveData<List<MovieEntity>>

    fun getMovieById(movieId: String): LiveData<MovieEntity>

    fun getAllTvShows(): LiveData<List<TvShowEntity>>

    fun getTvShowById(tvShowId: String): LiveData<TvShowEntity>

}