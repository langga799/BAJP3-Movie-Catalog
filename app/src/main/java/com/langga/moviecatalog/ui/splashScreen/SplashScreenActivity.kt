package com.langga.moviecatalog.ui.splashScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.langga.moviecatalog.databinding.ActivitySplashScreenBinding
import com.langga.moviecatalog.ui.home.HomeActivity
import com.langga.moviecatalog.ui.settings.SettingPreference
import com.langga.moviecatalog.ui.settings.ThemeViewModel
import com.langga.moviecatalog.ui.settings.ViewModelFactory


class SplashScreenActivity : AppCompatActivity() {

    private val Context.dataStorePref: DataStore<Preferences> by preferencesDataStore(name = "theme_setting")
    private lateinit var binding: ActivitySplashScreenBinding

    companion object {
        const val DELAY = 1500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val preference = SettingPreference.getInstance(dataStorePref)
        val themeViewModel =
            ViewModelProvider(this, ViewModelFactory(preference))[ThemeViewModel::class.java]

        themeViewModel.getThemeSetting().observe(this, { onDarkTheme ->
            if (onDarkTheme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }, DELAY)

    }
}