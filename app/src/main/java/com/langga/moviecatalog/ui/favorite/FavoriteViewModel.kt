package com.langga.moviecatalog.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity

class FavoriteViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> {
        return movieTvRepository.getFavoriteMovie()
    }

    fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>> {
        return movieTvRepository.getFavoriteTvShow()
    }

    fun setPerFavoriteMovie(movieEntity: MovieEntity) {
        val state = movieEntity.favoriteMovie.not()
        movieTvRepository.setPerFavoriteMovie(movieEntity, state)
    }

    fun setPerFavoriteTvShow(tvShowEntity: TvShowEntity) {
        val state = tvShowEntity.favoriteTvShow.not()
        movieTvRepository.setPerFavoriteTvShow(tvShowEntity, state)
    }
}