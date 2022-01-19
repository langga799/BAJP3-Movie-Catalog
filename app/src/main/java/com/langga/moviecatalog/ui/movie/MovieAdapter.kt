package com.langga.moviecatalog.ui.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.langga.moviecatalog.R
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.databinding.ItemListBinding
import com.langga.moviecatalog.ui.detail.DetailActivity

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    companion object {
        const val MOVIES = "MOVIES"
    }

    private var listMovie = ArrayList<MovieEntity>()

    fun setMovies(movie: List<MovieEntity>?) {
        if (movie == null) return
        this.listMovie.clear()
        this.listMovie.addAll(movie)
    }

    inner class MovieViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {
                tvTitle.text = movie.originalTitle
                tvReleaseDate.text = movie.releaseDate
                tvRating.text = movie.voteAverage.toString()

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_ID_MOVIE, movie.id)
                    intent.putExtra(DetailActivity.TYPE, MOVIES)
                    itemView.context.startActivity(intent)
                }

                Glide.with(itemView.context)
                    .load(DetailActivity.IMAGE_PATH + movie.posterPath)
                    .centerCrop()
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(ivPoster)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MovieAdapter.MovieViewHolder {
        val itemBinding =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listMovie.size
}