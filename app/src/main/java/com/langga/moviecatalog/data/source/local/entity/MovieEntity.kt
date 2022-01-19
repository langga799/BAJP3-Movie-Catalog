package com.langga.moviecatalog.data.source.local.entity

data class MovieEntity(
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val backDropPath: String,
    val voteAverage: Double,
    val voteCount: Int,
)