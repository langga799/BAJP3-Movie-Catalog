package com.langga.moviecatalog.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.langga.moviecatalog.data.source.local.LocalDataSource
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.data.source.remote.ApiResponse
import com.langga.moviecatalog.data.source.remote.RemoteDataSource
import com.langga.moviecatalog.data.source.remote.response.ResultsItemMovie
import com.langga.moviecatalog.data.source.remote.response.ResultsItemTvShow
import com.langga.moviecatalog.utils.AppExecutors
import com.langga.moviecatalog.vo.Resource

class MovieTvRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : MovieTvDataSource {

    companion object {
        @Volatile
        private var instance: MovieTvRepository? = null

        fun getInstance(
            dataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors,
        ): MovieTvRepository =
            instance ?: synchronized(this) {
                instance ?: MovieTvRepository(dataSource, localDataSource, appExecutors)
            }
    }

    override fun getAllMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<ResultsItemMovie>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(5)
                    .setPageSize(5)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<ResultsItemMovie>>> {
                return remoteDataSource.getListMovies()
            }

            override fun saveCallResult(data: List<ResultsItemMovie>) {
                val listMovie = ArrayList<MovieEntity>()
                for (movie in data) {
                    val dataMovie = MovieEntity(
                        movie.id,
                        movie.originalLanguage,
                        movie.originalTitle,
                        movie.overview,
                        movie.popularity,
                        movie.posterPath,
                        movie.releaseDate,
                        movie.backdropPath,
                        movie.voteAverage,
                        movie.voteCount,
                        favoriteMovie = false
                    )
                    listMovie.add(dataMovie)
                }
                localDataSource.insertPageMovie(listMovie)
            }
        }.asLiveData()

    }

    override fun getMovieById(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, List<ResultsItemMovie>>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> {
                return localDataSource.getMovieById(movieId)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<List<ResultsItemMovie>>> {
                return remoteDataSource.getListMovies()
            }

            override fun saveCallResult(data: List<ResultsItemMovie>) {
                lateinit var dataMovie: MovieEntity
                for (movie in data) {
                    if (movieId == movie.id) {
                        dataMovie = MovieEntity(
                            movie.id,
                            movie.originalLanguage,
                            movie.originalTitle,
                            movie.overview,
                            movie.popularity,
                            movie.posterPath,
                            movie.releaseDate,
                            movie.backdropPath,
                            movie.voteAverage,
                            movie.voteCount,
                            favoriteMovie = false
                        )
                    }
                }
                localDataSource.updateMovie(dataMovie)
            }
        }.asLiveData()
    }

    override fun getAllTvShows(sort: String): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TvShowEntity>, List<ResultsItemTvShow>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(5)
                    .setPageSize(5)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvShow(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<ResultsItemTvShow>>> {
                return remoteDataSource.getListTvShow()
            }

            override fun saveCallResult(data: List<ResultsItemTvShow>) {
                val listTvShow = ArrayList<TvShowEntity>()
                for (tvShow in data) {
                    val dataMovie = TvShowEntity(
                        tvShow.id,
                        tvShow.originalLanguage,
                        tvShow.originalName,
                        tvShow.overview,
                        tvShow.popularity,
                        tvShow.posterPath,
                        tvShow.firstAirDate,
                        tvShow.backdropPath,
                        tvShow.voteAverage,
                        tvShow.voteCount,
                        favoriteTvShow = false
                    )
                    listTvShow.add(dataMovie)
                }
                localDataSource.insertPageTvShow(listTvShow)
            }
        }.asLiveData()
    }

    override fun getTvShowById(tvShowId: Int): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, List<ResultsItemTvShow>>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowEntity> {
                return localDataSource.getTvShowById(tvShowId)
            }

            override fun shouldFetch(data: TvShowEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<List<ResultsItemTvShow>>> {
                return remoteDataSource.getListTvShow()
            }

            override fun saveCallResult(data: List<ResultsItemTvShow>) {
                lateinit var dataTvShow: TvShowEntity
                for (tvShow in data) {
                    if (tvShowId == tvShow.id) {
                        dataTvShow = TvShowEntity(
                            tvShow.id,
                            tvShow.originalLanguage,
                            tvShow.originalName,
                            tvShow.overview,
                            tvShow.popularity,
                            tvShow.posterPath,
                            tvShow.firstAirDate,
                            tvShow.backdropPath,
                            tvShow.voteAverage,
                            tvShow.voteCount,
                            favoriteTvShow = false
                        )
                    }
                }
                localDataSource.updateTvShow(dataTvShow)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5)
            .setPageSize(5)
            .build()
        return LivePagedListBuilder(localDataSource.getAllFavoriteMovie(), config).build()
    }

    override fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5)
            .setPageSize(5)
            .build()
        return LivePagedListBuilder(localDataSource.getAllFavoriteTvShow(), config).build()
    }

    override fun setPerFavoriteMovie(movie: MovieEntity, state: Boolean) {
        return appExecutors.diskIO().execute {
            localDataSource.setPerMovieFavorite(movie, state)
        }
    }

    override fun setPerFavoriteTvShow(tvShow: TvShowEntity, state: Boolean) {
        return appExecutors.diskIO().execute {
            localDataSource.setPerTvShowFavorite(tvShow, state)
        }
    }


}