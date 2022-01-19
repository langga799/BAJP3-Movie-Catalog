package com.langga.moviecatalog.di

import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.remote.RemoteDataSource


object Injection {

    fun provideRepository(): MovieTvRepository {
        val remoteDataSource = RemoteDataSource()
        return MovieTvRepository(remoteDataSource)
    }
}