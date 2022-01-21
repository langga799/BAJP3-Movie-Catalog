package com.langga.moviecatalog.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.vo.Resource

class TvShowViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    fun getTvShow(sort:String): LiveData<Resource<PagedList<TvShowEntity>>> {
        return movieTvRepository.getAllTvShows(sort)
    }

}