package com.langga.moviecatalog.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ThemeViewModel(private val preference: SettingPreference) : ViewModel() {

    fun getThemeSetting(): LiveData<Boolean> {
        return preference.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            preference.saveThemeSetting(isDarkMode)
        }
    }
}