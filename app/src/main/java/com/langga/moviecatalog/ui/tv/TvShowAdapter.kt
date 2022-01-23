package com.langga.moviecatalog.ui.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.langga.moviecatalog.R
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.databinding.ItemListBinding
import com.langga.moviecatalog.ui.detail.DetailActivity

class TvShowAdapter : PagedListAdapter<TvShowEntity, TvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        const val TV = "TV"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class TvShowViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity) {
            with(binding) {
                tvTitle.text = tvShow.originalName
                tvReleaseDate.text = tvShow.firstAirDate
                tvRating.text = tvShow.voteAverage.toString()

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_ID_TV, tvShow.id)
                    intent.putExtra(DetailActivity.TYPE, TV)
                    itemView.context.startActivity(intent)
                }

                Glide.with(itemView.context)
                    .load(DetailActivity.POSTER_PATH + tvShow.posterPath)
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
    ): TvShowAdapter.TvShowViewHolder {
        val itemBinding =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TvShowAdapter.TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }

}
