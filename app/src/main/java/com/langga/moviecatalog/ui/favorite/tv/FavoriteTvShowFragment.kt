package com.langga.moviecatalog.ui.favorite.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.langga.moviecatalog.databinding.FragmentTvShowBinding


class FavoriteTvShowFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }


}