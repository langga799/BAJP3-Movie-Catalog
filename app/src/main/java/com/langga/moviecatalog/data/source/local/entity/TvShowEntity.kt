package com.langga.moviecatalog.data.source.local.entity

data class TvShowEntity(
    val id: Int,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val firstAirDate: String,
    val backDropPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
)
