package com.langga.moviecatalog.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity

class TvShowViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    fun getTvShow(): LiveData<List<TvShowEntity>> = movieTvRepository.getAllTvShows()

}