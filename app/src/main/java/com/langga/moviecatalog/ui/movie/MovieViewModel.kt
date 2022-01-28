package com.langga.moviecatalog.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.vo.Resource

class MovieViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    fun getMovies(sort: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return movieTvRepository.getAllMovies(sort)
    }

}