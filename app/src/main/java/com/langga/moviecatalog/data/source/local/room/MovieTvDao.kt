package com.langga.moviecatalog.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity


@Dao
interface MovieTvDao {

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getAllMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieEntity>

    @Transaction
    @Query("SELECT * FROM movie_entities WHERE id = :movieId")
    fun getMovieById(movieId: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM movie_entities WHERE favoriteMovie = 1")
    fun getAllFavoriteMovie(): DataSource.Factory<Int, MovieEntity>


    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getAllTvShows(query: SupportSQLiteQuery): DataSource.Factory<Int, TvShowEntity>

    @Transaction
    @Query("SELECT * FROM tv_show_entities WHERE id = :tvShowId")
    fun getTvShowById(tvShowId: Int): LiveData<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShow: List<TvShowEntity>)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)

    @Query("SELECT * FROM tv_show_entities WHERE favoriteTvShow = 1")
    fun getAllFavoriteTvShow(): DataSource.Factory<Int, TvShowEntity>


}