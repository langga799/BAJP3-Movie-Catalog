package com.langga.moviecatalog.ui.favorite.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.langga.moviecatalog.R
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.databinding.ItemListBinding
import com.langga.moviecatalog.ui.detail.DetailActivity

class FavoriteMovieAdapter :
    PagedListAdapter<MovieEntity, FavoriteMovieAdapter.FavoriteMovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        const val MOVIES = "MOVIES"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class FavoriteMovieViewHolder(private val binding: ItemListBinding) :
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
                    .load(DetailActivity.POSTER_PATH + movie.posterPath)
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
    ): FavoriteMovieAdapter.FavoriteMovieViewHolder {
        val itemBinding =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: FavoriteMovieAdapter.FavoriteMovieViewHolder,
        position: Int,
    ) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }
}