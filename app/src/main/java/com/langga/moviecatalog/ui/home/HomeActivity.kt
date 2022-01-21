package com.langga.moviecatalog.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.langga.moviecatalog.R
import com.langga.moviecatalog.databinding.ActivityHomeBinding
import com.langga.moviecatalog.ui.favorite.FavoriteActivity
import com.langga.moviecatalog.ui.movie.MovieFragment
import com.langga.moviecatalog.ui.settings.SettingActivity
import com.langga.moviecatalog.ui.tv.TvShowFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0F
        supportActionBar?.displayOptions

        setupViewPagerAdapter()

    }

    private fun setupViewPagerAdapter() {
        val adapter = TabViewPagerAdapter(this)
        val viewPager2 = binding.viewPager2
        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 1

        adapter.addFragment(
            arrayListOf(
                MovieFragment(),
                TvShowFragment()
            )
        )

        val tabTitle = arrayOf(
            R.string.movies,
            R.string.tv_show
        )

        TabLayoutMediator(
            binding.tabs, viewPager2
        ) { tab, position ->
            tab.setText(tabTitle[position])
        }.attach()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting_menu -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
            R.id.favorite_film -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

