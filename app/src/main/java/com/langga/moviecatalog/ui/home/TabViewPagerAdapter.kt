package com.langga.moviecatalog.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var fragment = arrayListOf<Fragment>()

    fun addFragment(listFragment: ArrayList<Fragment>) {
        fragment.clear()
        fragment.addAll(listFragment)
    }

    override fun getItemCount(): Int = fragment.size

    override fun createFragment(position: Int): Fragment = fragment[position]

}