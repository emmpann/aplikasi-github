package com.github.emmpann.aplikasigithubuser.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.emmpann.aplikasigithubuser.data.local.preferences.SettingsPreferences

class SettingsViewModelFactory(private val pref: SettingsPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) return SettingsViewModel(pref) as T
        throw IllegalArgumentException("Unknowns ViewModel class: ${modelClass.name}")
    }

}