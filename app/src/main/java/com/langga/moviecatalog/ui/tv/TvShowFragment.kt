package com.langga.moviecatalog.ui.tv

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.langga.moviecatalog.databinding.FragmentTvShowBinding
import com.langga.moviecatalog.ui.viewmodel.ViewModelFactory


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

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(requireActivity(), factory)[TvShowViewModel::class.java]

        binding.loadingInTv.visibility = View.VISIBLE
        binding.rvTvShows.visibility = View.GONE
        if (activity != null) {
            viewModel.getTvShow().observe(viewLifecycleOwner, { listTvShow ->
                binding.loadingInTv.visibility = View.GONE
                binding.rvTvShows.visibility = View.VISIBLE
                tvShowAdapter.setTvShow(listTvShow)
                tvShowAdapter.notifyDataSetChanged()
            })
        }

    }


}