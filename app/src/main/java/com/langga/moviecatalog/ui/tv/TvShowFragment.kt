package com.langga.moviecatalog.ui.tv

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.langga.moviecatalog.data.source.local.entity.MovieEntity
import com.langga.moviecatalog.data.source.local.entity.TvShowEntity
import com.langga.moviecatalog.databinding.FragmentTvShowBinding
import com.langga.moviecatalog.ui.viewmodel.ViewModelFactory
import com.langga.moviecatalog.utils.SortUtils
import com.langga.moviecatalog.vo.Resource
import com.langga.moviecatalog.vo.Status


class TvShowFragment : Fragment() {

    private lateinit var binding: FragmentTvShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvShowAdapter = TvShowAdapter()
        binding.rvTvShows.adapter = tvShowAdapter
        binding.rvTvShows.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvTvShows.setHasFixedSize(true)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(requireActivity(), factory)[TvShowViewModel::class.java]

        val tvShowObserver = Observer<Resource<PagedList<TvShowEntity>>>{ tvShowData ->
            if (tvShowData != null){
                when (tvShowData.status){
                    Status.LOADING -> {
                        binding.loadingInTv.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.loadingInTv.visibility = View.GONE
                        tvShowAdapter.submitList(tvShowData.data)
                    }
                    Status.ERROR -> {
                        binding.loadingInTv.visibility = View.GONE
                        Toast.makeText(requireActivity(), "Unknown error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        viewModel.getTvShow(SortUtils.RATING).observe(viewLifecycleOwner, tvShowObserver)


    }


}