package com.langga.moviecatalog.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getListMovies(): LiveData<ApiResponse<List<ResultsItemMovie>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<ResultsItemMovie>>>()
        NetworkConfig.getApiService().getDataMovies(BuildConfig.API_KEY)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>,
                ) {
                    if (response.isSuccessful) {
                        val dataMovie = response.body()?.results!!
                        Log.d(TAG, dataMovie.toString())
                        resultMovie.postValue(ApiResponse.success(dataMovie))
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure : ${t.message.toString()}")
                    EspressoIdlingResource.decrement()
                }
            })
        return resultMovie
    }

    fun getListTvShow(): LiveData<ApiResponse<List<ResultsItemTvShow>>> {
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<List<ResultsItemTvShow>>>()
        NetworkConfig.getApiService().getDataTvShow(BuildConfig.API_KEY)
            .enqueue(object : Callback<TvShowResponse> {
                override fun onResponse(
                    call: Call<TvShowResponse>,
                    response: Response<TvShowResponse>,
                ) {
                    if (response.isSuccessful) {
                        val dataTvShow = response.body()?.results!!
                        resultTvShow.postValue(ApiResponse.success(dataTvShow))
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure : ${t.message.toString()}")
                    EspressoIdlingResource.decrement()
                }

            })
        return resultTvShow
    }


}