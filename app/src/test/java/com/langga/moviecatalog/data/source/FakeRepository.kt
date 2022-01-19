package com.langga.moviecatalog.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.data.source.remote.RemoteDataSource
import com.langga.moviecatalog.data.source.remote.response.ResultsItemMovie
import com.langga.moviecatalog.data.source.remote.response.ResultsItemTvShow

class FakeRepository(private val remoteDataSource: RemoteDataSource) : MovieTvDataSource {

    override fun getAllMovies(): LiveData<List<MovieEntity>> {
        val resultMovie = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getListMovies(object : RemoteDataSource.LoadMovieCallback {
            override fun onAllMovieReceived(movieItemResponse: List<ResultsItemMovie>) {
                val listMovie = ArrayList<MovieEntity>()
                for (movie in movieItemResponse) {
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
                        movie.voteCount
                    )
                    listMovie.add(dataMovie)
                }
                resultMovie.postValue(listMovie)
            }

        })
        return resultMovie
    }

    override fun getMovieById(movieId: String): LiveData<MovieEntity> {
        val resultMovie = MutableLiveData<MovieEntity>()
        remoteDataSource.getListMovies(object : RemoteDataSource.LoadMovieCallback {
            override fun onAllMovieReceived(movieItemResponse: List<ResultsItemMovie>) {
                lateinit var dataMovie: MovieEntity
                for (movie in movieItemResponse) {
                    if (movieId == movie.id.toString()) {
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
                            movie.voteCount
                        )
                    }

                }
                resultMovie.postValue(dataMovie)
            }

        })
        return resultMovie
    }

    override fun getAllTvShows(): LiveData<List<TvShowEntity>> {
        val resultTvShow = MutableLiveData<List<TvShowEntity>>()
        remoteDataSource.getListTvShow(object : RemoteDataSource.LoadTvShowCallback {
            override fun onAllTvShowReceived(tvShowItemResponse: List<ResultsItemTvShow>) {
                val listTvShow = ArrayList<TvShowEntity>()
                for (tvShow in tvShowItemResponse) {
                    val dataTvShow = TvShowEntity(
                        tvShow.id,
                        tvShow.originalLanguage,
                        tvShow.originalName,
                        tvShow.overview,
                        tvShow.popularity,
                        tvShow.posterPath,
                        tvShow.firstAirDate,
                        tvShow.backdropPath,
                        tvShow.voteAverage,
                        tvShow.voteCount
                    )
                    listTvShow.add(dataTvShow)
                }
                resultTvShow.postValue(listTvShow)
            }

        })
        return resultTvShow
    }

    override fun getTvShowById(tvShowId: String): LiveData<TvShowEntity> {
        val resultTvShow = MutableLiveData<TvShowEntity>()
        remoteDataSource.getListTvShow(object : RemoteDataSource.LoadTvShowCallback {
            override fun onAllTvShowReceived(tvShowItemResponse: List<ResultsItemTvShow>) {
                lateinit var dataTvShow: TvShowEntity
                for (tvShow in tvShowItemResponse) {
                    if (tvShowId == tvShow.id.toString()) {
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
                            tvShow.voteCount
                        )
                    }
                }
                resultTvShow.postValue(dataTvShow)
            }

        })
        return resultTvShow
    }

}