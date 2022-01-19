package com.langga.moviecatalog.data.source.remote

import android.util.Log
import com.langga.moviecatalog.BuildConfig
import com.langga.moviecatalog.data.source.remote.response.MovieResponse
import com.langga.moviecatalog.data.source.remote.response.ResultsItemMovie
import com.langga.moviecatalog.data.source.remote.response.ResultsItemTvShow
import com.langga.moviecatalog.data.source.remote.response.TvShowResponse
import com.langga.moviecatalog.network.NetworkConfig
import com.langga.moviecatalog.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    companion object {
        private const val TAG = "RemoteDataSource"
    }

    fun getListMovies(callback: LoadMovieCallback) {
        EspressoIdlingResource.increment()
        NetworkConfig.getApiService().getDataMovies(BuildConfig.API_KEY)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>,
                ) {
                    if (response.isSuccessful) {
                        val dataMovie = response.body()?.results!!
                        callback.onAllMovieReceived(dataMovie)
                        EspressoIdlingResource.decrement()
                    }

                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure : ${t.message.toString()}")
                    EspressoIdlingResource.decrement()
                }

            })
    }

    fun getListTvShow(callback: LoadTvShowCallback) {
        EspressoIdlingResource.increment()
        NetworkConfig.getApiService().getDataTvShow(BuildConfig.API_KEY)
            .enqueue(object : Callback<TvShowResponse> {
                override fun onResponse(
                    call: Call<TvShowResponse>,
                    response: Response<TvShowResponse>,
                ) {
                    if (response.isSuccessful) {
                        val dataTvShow = response.body()?.results!!
                        callback.onAllTvShowReceived(dataTvShow)
                        EspressoIdlingResource.decrement()
                    }

                }

                override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure : ${t.message.toString()}")
                    EspressoIdlingResource.decrement()
                }

            })
    }

    interface LoadMovieCallback {
        fun onAllMovieReceived(movieItemResponse: List<ResultsItemMovie>)
    }

    interface LoadTvShowCallback {
        fun onAllTvShowReceived(tvShowItemResponse: List<ResultsItemTvShow>)
    }
}