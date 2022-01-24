package com.langga.moviecatalog.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.langga.moviecatalog.R
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.databinding.FragmentMovieBinding
import com.langga.moviecatalog.ui.viewmodel.ViewModelFactory
import com.langga.moviecatalog.utils.SortUtils
import com.langga.moviecatalog.vo.Resource
import com.langga.moviecatalog.vo.Status

class MovieFragment : Fragment() {

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
        val viewModel = ViewModelProvider(requireActivity(), factory)[MovieViewModel::class.java]

        val movieAdapter = MovieAdapter()
        binding?.rvMovies?.adapter = movieAdapter
        binding?.rvMovies?.layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvMovies?.setHasFixedSize(true)

        val movieObserver = Observer<Resource<PagedList<MovieEntity>>> { movieData ->
            if (movieData != null) {
                when (movieData.status) {
                    Status.LOADING -> {
                        binding?.loadingInMovie?.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding?.loadingInMovie?.visibility = View.GONE
                        movieAdapter.submitList(movieData.data)
                    }
                    Status.ERROR -> {
                        binding?.loadingInMovie?.visibility = View.GONE
                        Toast.makeText(requireActivity(), "Unknown error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        val spinner = binding?.spinnerFilterMovie
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.filter_item,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner?.adapter = adapter
        }

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            ) {
                when (position) {
                    0 -> {
                        viewModel.getMovies(SortUtils.RATING)
                            .observe(viewLifecycleOwner, movieObserver)
                    }
                    1 -> {
                        viewModel.getMovies(SortUtils.NEWEST)
                            .observe(viewLifecycleOwner, movieObserver)
                    }
                    2 -> {
                        viewModel.getMovies(SortUtils.OLDEST)
                            .observe(viewLifecycleOwner, movieObserver)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


    }

}