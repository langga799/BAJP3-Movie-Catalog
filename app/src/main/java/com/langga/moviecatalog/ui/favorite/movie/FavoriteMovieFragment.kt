package com.langga.moviecatalog.ui.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.databinding.FragmentMovieBinding
import com.langga.moviecatalog.ui.detail.DetailViewModel
import com.langga.moviecatalog.ui.favorite.FavoriteViewModel
import com.langga.moviecatalog.ui.viewmodel.ViewModelFactory


class FavoriteMovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        favoriteViewModel.getFavoriteMovie().observe(viewLifecycleOwner, { dataFavoriteMovie ->
            setupRecyclerView(dataFavoriteMovie)
        })
    }

    private fun setupRecyclerView(movieEntity: PagedList<MovieEntity>) {
        val adapter = FavoriteMovieAdapter()
        binding?.rvMovies?.layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvMovies?.adapter = adapter
        adapter.submitList(movieEntity)

        if (adapter.itemCount == 0){
            Toast.makeText(requireActivity(), "Data is empty!", Toast.LENGTH_SHORT).show()
        }
    }


}