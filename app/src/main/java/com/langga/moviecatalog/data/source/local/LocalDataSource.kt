package com.langga.moviecatalog.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.data.source.local.room.MovieTvDao
import com.langga.moviecatalog.utils.SortUtils

class LocalDataSource private constructor(private val mMovieTvDao: MovieTvDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieTvDao: MovieTvDao): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocalDataSource(movieTvDao)
            }
            return INSTANCE as LocalDataSource
        }
    }

    fun getAllMovies(sort: String): DataSource.Factory<Int, MovieEntity> {
        val query = SortUtils.getSortedQueryMovie(sort)
        return mMovieTvDao.getAllMovies(query)
    }

    fun getAllTvShow(sort: String): DataSource.Factory<Int, TvShowEntity> {
        val query = SortUtils.getSortedQueryTvShow(sort)
        return mMovieTvDao.getAllTvShows(query)
    }

    fun getAllFavoriteMovie(): DataSource.Factory<Int, MovieEntity> {
        return mMovieTvDao.getAllFavoriteMovie()
    }

    fun getAllFavoriteTvShow(): DataSource.Factory<Int, TvShowEntity> {
        return mMovieTvDao.getAllFavoriteTvShow()
    }

    fun setPerMovieFavorite(movie: MovieEntity, state: Boolean) {
        movie.favoriteMovie = state
        mMovieTvDao.updateMovie(movie)
    }

    fun setPerTvShowFavorite(tvShow: TvShowEntity, state: Boolean) {
        tvShow.favoriteTvShow = state
        mMovieTvDao.updateTvShow(tvShow)
    }

    fun getMovieById(movieId: Int): LiveData<MovieEntity> = mMovieTvDao.getMovieById(movieId)

    fun getTvShowById(tvShowId: Int): LiveData<TvShowEntity> = mMovieTvDao.getTvShowById(tvShowId)

    fun insertPageMovie(movies: List<MovieEntity>) = mMovieTvDao.insertMovie(movies)

    fun insertPageTvShow(tvShow: List<TvShowEntity>) = mMovieTvDao.insertTvShow(tvShow)

    fun updateMovie(movie: MovieEntity) = mMovieTvDao.updateMovie(movie)

    fun updateTvShow(tvShow: TvShowEntity) = mMovieTvDao.updateTvShow(tvShow)

}