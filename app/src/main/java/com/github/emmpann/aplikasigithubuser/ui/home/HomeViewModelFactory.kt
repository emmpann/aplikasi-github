package com.github.emmpann.aplikasigithubuser.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.emmpann.aplikasigithubuser.data.local.preferences.SettingsPreferences

class HomeViewModelFactory(private val pref: SettingsPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) return HomeViewModel(pref) as T
        throw IllegalArgumentException("Unknowns ViewModel class: " + modelClass.name)
    }
}