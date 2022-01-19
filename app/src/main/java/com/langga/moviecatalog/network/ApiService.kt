package com.langga.moviecatalog.network

import com.langga.moviecatalog.data.source.remote.response.MovieResponse
import com.langga.moviecatalog.data.source.remote.response.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie")
    fun getDataMovies(
        @Query("api_key") apiKey: String,
    ): Call<MovieResponse>

    @GET("tv")
    fun getDataTvShow(
        @Query("api_key") apiKey: String,
    ): Call<TvShowResponse>
}