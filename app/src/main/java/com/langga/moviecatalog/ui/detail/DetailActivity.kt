package com.langga.moviecatalog.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.langga.moviecatalog.R
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.databinding.ActivityDetailBinding
import com.langga.moviecatalog.ui.home.HomeActivity
import com.langga.moviecatalog.ui.viewmodel.ViewModelFactory
import com.langga.moviecatalog.utils.LoadImage.loadImage
import com.langga.moviecatalog.vo.Resource
import com.langga.moviecatalog.vo.Status


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.detail_overview)

        val factory = ViewModelFactory.getInstance(this)
        val detailViewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val idMovie = intent.extras?.getInt(EXTRA_ID_MOVIE)
        val idTvShow = intent.extras?.getInt(EXTRA_ID_TV)

        binding.loadingDetail.visibility = View.VISIBLE
        binding.pageDetail.visibility = View.GONE

        when (intent.extras?.getString(TYPE)) {
            MOVIES -> {
                if (idMovie != null) {
                    detailViewModel.getDataDetailMovies(idMovie)
                }
                detailViewModel.dataDetailMovie.observe(this, { dataMovie ->
                    showDetailMovie(dataMovie)
                })
            }
            TV -> {
                if (idTvShow != null) {
                    detailViewModel.getDetailTvShows(idTvShow)
                }
                detailViewModel.dataDetailTvShow.observe(this, { dataTvShow ->
                    showDetailTvShow(dataTvShow)
                })
            }
        }

        binding.fabFavorite.setOnClickListener {
            when (intent.extras?.getString(TYPE)) {
                MOVIES -> detailViewModel.setPerFavoriteMovie()
                TV -> detailViewModel.setPerFavoriteTvShow()
            }
        }
    }

    private fun setFavoriteMovie(state: Boolean) {
        when {
            state -> {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this, R.drawable.ic_favorite_fill
                    )
                )
            }
            else -> {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this, R.drawable.ic_favorite_border
                    )
                )
            }
        }
    }

    private fun showDetailMovie(movie: Resource<MovieEntity>) {
        when (movie.status) {
            Status.LOADING -> {
                binding.loadingDetail.visibility = View.VISIBLE
                binding.pageDetail.visibility = View.INVISIBLE
            }
            Status.SUCCESS -> {
                binding.loadingDetail.visibility = View.GONE
                binding.pageDetail.visibility = View.VISIBLE

                val state = movie.data?.favoriteMovie
                if (state != null) {
                    setFavoriteMovie(state)
                    setDataMovies(movie.data)
                }


            }
            Status.ERROR -> {
                Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showDetailTvShow(tvShow: Resource<TvShowEntity>) {
        when (tvShow.status) {
            Status.LOADING -> {
                binding.loadingDetail.visibility = View.VISIBLE
                binding.pageDetail.visibility = View.INVISIBLE
            }
            Status.SUCCESS -> {
                binding.loadingDetail.visibility = View.GONE
                binding.pageDetail.visibility = View.VISIBLE

                val state = tvShow.data?.favoriteTvShow
                if (state != null) {
                    setFavoriteMovie(state)
                    setDataTvShow(tvShow.data)
                }

            }
            Status.ERROR -> {
                Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setDataMovies(movieEntity: MovieEntity) {
        binding.apply {
            tvTitleDetail.text = movieEntity.originalTitle
            tvPopularityDetail.text = movieEntity.popularity.toString()
            tvDateDetail.text = movieEntity.releaseDate
            tvRatingDetail.text = movieEntity.voteAverage.toString()
            descriptionDetail.text = movieEntity.overview
            loadImage("$POSTER_PATH${movieEntity.posterPath}", ivPosterDetail)
            loadImage("$BACKGROUND_PATH${movieEntity.backDropPath}", ivBackgroundDetail)
        }
    }

    private fun setDataTvShow(tvShowEntity: TvShowEntity) {
        binding.apply {
            tvTitleDetail.text = tvShowEntity.originalName
            tvPopularityDetail.text = tvShowEntity.popularity.toString()
            tvDateDetail.text = tvShowEntity.firstAirDate
            tvRatingDetail.text = tvShowEntity.voteAverage.toString()
            descriptionDetail.text = tvShowEntity.overview
            loadImage("$POSTER_PATH${tvShowEntity.posterPath}", ivPosterDetail)
            loadImage("$BACKGROUND_PATH${tvShowEntity.backDropPath}", ivBackgroundDetail)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val MOVIES = "MOVIES"
        const val TV = "TV"
        const val EXTRA_ID_MOVIE = "extra_id"
        const val EXTRA_ID_TV = "extra_id_tv"
        const val TYPE = "type"
        const val POSTER_PATH = "https://image.tmdb.org/t/p/w500"
        const val BACKGROUND_PATH = "https://image.tmdb.org/t/p/w500"
    }
}