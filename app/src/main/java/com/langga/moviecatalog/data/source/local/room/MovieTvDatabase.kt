package com.langga.moviecatalog.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 1, exportSchema = false)
abstract class MovieTvDatabase : RoomDatabase() {

    abstract fun movieTvDao(): MovieTvDao

    companion object {
        @Volatile
        private var INSTANCE: MovieTvDatabase? = null

        fun getInstance(context: Context): MovieTvDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MovieTvDatabase::class.java,
                    "movie_tv_database"
                ).build()
            }
    }
}