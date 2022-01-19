package com.langga.moviecatalog.utils

import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.data.source.remote.response.ResultsItemMovie
import com.langga.moviecatalog.data.source.remote.response.ResultsItemTvShow

object DataLocal {

    fun generateLocalMovies(): List<MovieEntity> {
        val movies = ArrayList<MovieEntity>()

        movies.add(
            MovieEntity(
                634649,
                "en",
                "Spider-Man: No Way Home",
                "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
                8817.063,
                "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
                "2021-12-15",
                "/1Rr5SrvHxMXHu5RjKpaMba8VTzi.jpg",
                8.4,
                3427
            )
        )
        return movies
    }

    fun generateRemoteLocalMovies(): List<ResultsItemMovie> {
        val movies = ArrayList<ResultsItemMovie>()

        movies.add(
            ResultsItemMovie(
                "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
                "en",
                "Spider-Man: No Way Home",
                "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
                "/1Rr5SrvHxMXHu5RjKpaMba8VTzi.jpg",
                "2021-12-15",
                8817.063,
                8.4,
                634649,
                3427
            )
        )
        return movies
    }

    fun generateLocalTvShow(): List<TvShowEntity> {
        val tvShow = ArrayList<TvShowEntity>()

        tvShow.add(
            TvShowEntity(
                77169,
                "en",
                "Cobra Kai",
                "This Karate Kid sequel series picks up 30 years after the events of the 1984 All Valley Karate Tournament and finds Johnny Lawrence on the hunt for redemption by reopening the infamous Cobra Kai karate dojo. This reignites his old rivalry with the successful Daniel LaRusso, who has been working to maintain the balance in his life without mentor Mr. Miyagi.",
                4668.244,
                "/6POBWybSBDBKjSs1VAQcnQC1qyt.jpg",
                "2018-05-02",
                "/35SS0nlBhu28cSe7TiO3ZiywZhl.jpg",
                8.1,
                3818
            )
        )


        return tvShow
    }

    fun generateRemoteLocalTvShow(): List<ResultsItemTvShow> {
        val tvShow = ArrayList<ResultsItemTvShow>()

        tvShow.add(
            ResultsItemTvShow(
                "2021-11-24",
                "Former Avenger Clint Barton has a seemingly simple mission: get back to his family for Christmas. Possible? Maybe with the help of Kate Bishop, a 22-year-old archer with dreams of becoming a superhero. The two are forced to work together when a presence from Barton’s past threatens to derail far more than the festive spirit.",
                "en",
                "/pqzjCxPVc9TkVgGRWeAoMmyqkZV.jpg",
                "/1R68vl3d5s86JsS2NPjl8UoMqIS.jpg",
                "Hawkeye",
                1758.15,
                8.5,
                1252,
                88329,
            )
        )
        return tvShow
    }

}