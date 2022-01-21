package com.langga.moviecatalog.di

import android.content.Context
import com.langga.moviecatalog.data.source.MovieTvRepository
import com.langga.moviecatalog.data.source.local.LocalDataSource
import com.langga.moviecatalog.data.source.local.room.MovieTvDatabase
import com.langga.moviecatalog.data.source.remote.RemoteDataSource
import com.langga.moviecatalog.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): MovieTvRepository {

        val database = MovieTvDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieTvDao())
        val appExecutors = AppExecutors()

        return MovieTvRepository.getInstance(
            remoteDataSource,
            localDataSource,
            appExecutors
        )
    }
}