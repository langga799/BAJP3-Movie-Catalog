package com.langga.moviecatalog.ui.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.langga.moviecatalog.databinding.FragmentMovieBinding
import com.langga.moviecatalog.ui.viewmodel.ViewModelFactory

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieAdapter = MovieAdapter()
        binding.rvMovies.adapter = movieAdapter
        binding.rvMovies.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvMovies.setHasFixedSize(true)

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(requireActivity(), factory)[MovieViewModel::class.java]

        binding.loadingInMovie.visibility = View.VISIBLE
        binding.rvMovies.visibility = View.GONE
        if (activity != null) {
            viewModel.getMovies().observe(viewLifecycleOwner, { listMovie ->
                binding.loadingInMovie.visibility = View.GONE
                binding.rvMovies.visibility = View.VISIBLE
                movieAdapter.setMovies(listMovie)
               // DiffUtil.Callback
            })
        }


    }


}