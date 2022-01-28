package com.langga.moviecatalog.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {

    const val NEWEST = "newest"
    const val OLDEST = "oldest"
    const val RATING = "rating"

    fun getSortedQueryMovie(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM movie_entities ")
        when (filter) {
            NEWEST -> simpleQuery.append("ORDER BY releaseDate DESC")
            OLDEST -> simpleQuery.append("ORDER BY releaseDate ASC")
            RATING -> simpleQuery.append("ORDER BY voteAverage DESC")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getSortedQueryTvShow(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM tv_show_entities ")
        when (filter) {
            NEWEST -> simpleQuery.append("ORDER BY firstAirDate DESC")
            OLDEST -> simpleQuery.append("ORDER BY firstAirDate ASC")
            RATING -> simpleQuery.append("ORDER BY voteAverage DESC")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

}