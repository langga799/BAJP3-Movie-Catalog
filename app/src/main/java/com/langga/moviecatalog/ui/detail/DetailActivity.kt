package com.langga.moviecatalog.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.langga.moviecatalog.R
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.databinding.ActivityDetailBinding
import com.langga.moviecatalog.ui.home.HomeActivity
import com.langga.moviecatalog.ui.viewmodel.ViewModelFactory
import com.langga.moviecatalog.utils.LoadImage.loadImage


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.detail_overview)

        val factory = ViewModelFactory.getInstance()
        val detailViewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val idMovie = intent.extras?.getInt(EXTRA_ID_MOVIE) ?: -1
        val idTvShow = intent.extras?.getInt(EXTRA_ID_TV) ?: -1

        binding.loadingDetail.visibility = View.VISIBLE
        binding.pageDetail.visibility = View.GONE
        when (intent.extras?.getString(TYPE)) {
            "MOVIES" -> {
                detailViewModel.getDataDetailMovies(idMovie.toString())
                    .observe(this, { movieDetail ->
                        binding.loadingDetail.visibility = View.GONE
                        binding.pageDetail.visibility = View.VISIBLE
                        setDataMovies(movieDetail)
                    })
            }
            "TV" -> {
                detailViewModel.getDetailTvShows(idTvShow.toString())
                    .observe(this, { tvShowDetail ->
                        binding.loadingDetail.visibility = View.GONE
                        binding.pageDetail.visibility = View.VISIBLE
                        setDataTvShow(tvShowDetail)
                    })
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
            loadImage("$IMAGE_PATH${movieEntity.posterPath}", ivPosterDetail)
        }
    }

    private fun setDataTvShow(tvShowEntity: TvShowEntity) {
        binding.apply {
            tvTitleDetail.text = tvShowEntity.originalName
            tvPopularityDetail.text = tvShowEntity.popularity.toString()
            tvDateDetail.text = tvShowEntity.firstAirDate
            tvRatingDetail.text = tvShowEntity.voteAverage.toString()
            descriptionDetail.text = tvShowEntity.overview
            loadImage("$IMAGE_PATH${tvShowEntity.posterPath}", ivPosterDetail)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, HomeActivity::class.java))
                finishAffinity()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_ID_MOVIE = "extra_id"
        const val EXTRA_ID_TV = "extra_id_tv"
        const val TYPE = "type"
        const val IMAGE_PATH = "https://image.tmdb.org/t/p/w500"
    }
}