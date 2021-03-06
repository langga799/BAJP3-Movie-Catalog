package com.langga.moviecatalog.ui.favorite.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.databinding.FragmentFavoriteTvShowBinding
import com.langga.moviecatalog.ui.favorite.FavoriteViewModel
import com.langga.moviecatalog.ui.viewmodel.ViewModelFactory


class FavoriteTvShowFragment : Fragment() {

    private var _binding: FragmentFavoriteTvShowBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteTvShowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        binding?.loadingInTvShow?.visibility = View.VISIBLE
        favoriteViewModel.getFavoriteTvShow().observe(viewLifecycleOwner, { dataFavoriteTvShow ->
            binding?.loadingInTvShow?.visibility = View.INVISIBLE
            setupRecyclerView(dataFavoriteTvShow)
        })
    }

    private fun setupRecyclerView(tvShowEntity: PagedList<TvShowEntity>) {
        val adapter = FavoriteTvShowAdapter()
        binding?.rvTvShowFavorite?.layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvTvShowFavorite?.adapter = adapter
        adapter.submitList(tvShowEntity)

        if (adapter.itemCount == 0) {
            binding?.tvMessageTvShow?.visibility = View.VISIBLE
        } else {
            binding?.tvMessageTvShow?.visibility = View.INVISIBLE
        }
    }


}


