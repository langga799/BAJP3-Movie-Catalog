package com.langga.moviecatalog.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.langga.moviecatalog.R
import com.langga.moviecatalog.databinding.ActivityFavoriteBinding
import com.langga.moviecatalog.ui.favorite.movie.FavoriteMovieFragment
import com.langga.moviecatalog.ui.favorite.tv.FavoriteTvShowFragment
import com.langga.moviecatalog.ui.home.HomeActivity
import com.langga.moviecatalog.ui.settings.SettingActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.favorite)
        supportActionBar?.elevation = 0F
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupFavoriteViewPager()

    }

    private fun setupFavoriteViewPager() {
        val adapter = FavoriteViewPagerAdapter(this)
        val favoriteViewPager = binding.favoriteViewPager
        favoriteViewPager.adapter = adapter
        favoriteViewPager.offscreenPageLimit = 1

        adapter.addFragment(
            arrayListOf(
                FavoriteMovieFragment(),
                FavoriteTvShowFragment()
            )
        )

        val tabTitle = arrayOf(
            getString(R.string.movies),
            getString(R.string.tv_show)
        )

        TabLayoutMediator(
            binding.tabsFavorite, favoriteViewPager
        ) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, HomeActivity::class.java))
                finishAffinity()
                return true
            }
            R.id.setting_menu -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}