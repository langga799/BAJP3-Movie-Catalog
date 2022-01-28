package com.langga.moviecatalog.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.langga.moviecatalog.databinding.ActivitySettingBinding
import com.langga.moviecatalog.ui.home.HomeActivity

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val Context.dataStorePreference: DataStore<Preferences> by preferencesDataStore(name = "change_theme")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Setting App"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        changeTheme()
        shareApplication()

    }

    private fun changeTheme() {
        val preferences = SettingPreference.getInstance(this.dataStorePreference)
        val themeViewModel =
            ViewModelProvider(this, ViewModelFactory(preferences))[ThemeViewModel::class.java]

        themeViewModel.getThemeSetting().observe(this, { isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        })

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }

    private fun shareApplication() {
        binding.btnShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Hello friends!, try this movie app now...")
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, HomeActivity::class.java))
                finishAffinity()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}