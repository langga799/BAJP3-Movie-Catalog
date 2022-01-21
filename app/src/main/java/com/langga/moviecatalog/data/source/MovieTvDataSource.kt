package com.langga.moviecatalog.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.vo.Resource

interface MovieTvDataSource {

    fun getAllMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>>

    fun getMovieById(movieId: Int): LiveData<Resource<MovieEntity>>

    fun getAllTvShows(sort: String): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getTvShowById(tvShowId: Int): LiveData<Resource<TvShowEntity>>

    fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>>

    fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>>

    fun setPerFavoriteMovie(movie: MovieEntity, state: Boolean)

    fun setPerFavoriteTvShow(tvShow: TvShowEntity, state: Boolean)


}