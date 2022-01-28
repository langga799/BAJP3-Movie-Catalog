package com.langga.moviecatalog.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.vo.Resource

class DetailViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    private val movieId = MutableLiveData<Int>()
    private val tvShowId = MutableLiveData<Int>()

    fun getDataDetailMovies(idMovie: Int): LiveData<Resource<MovieEntity>> {
        this.movieId.value = idMovie
        return movieTvRepository.getMovieById(idMovie)
    }

    fun getDetailTvShows(idTvShow: Int): LiveData<Resource<TvShowEntity>> {
        this.tvShowId.value = idTvShow
        return movieTvRepository.getTvShowById(idTvShow)
    }

    var dataDetailMovie: LiveData<Resource<MovieEntity>> =
        Transformations.switchMap(movieId) { idMovie ->
            movieTvRepository.getMovieById(idMovie)
        }

    var dataDetailTvShow: LiveData<Resource<TvShowEntity>> =
        Transformations.switchMap(tvShowId) { idTvShow ->
            movieTvRepository.getTvShowById(idTvShow)
        }

    fun setPerFavoriteMovie() {
        val movieResource = dataDetailMovie.value?.data
        if (movieResource != null) {
            val state = movieResource.favoriteMovie.not()
            movieTvRepository.setPerFavoriteMovie(movieResource, state)
        }
    }

    fun setPerFavoriteTvShow() {
        val tvShowResource = dataDetailTvShow.value?.data
        if (tvShowResource != null) {
            val state = tvShowResource.favoriteTvShow.not()
            movieTvRepository.setPerFavoriteTvShow(tvShowResource, state)
        }
    }

}