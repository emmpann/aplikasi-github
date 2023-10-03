package com.github.emmpann.aplikasigithubuser.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.emmpann.aplikasigithubuser.data.local.repository.FavoriteUsersRepository

class FavoriteUserViewModelFactory(private val repo: FavoriteUsersRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) return FavoriteUserViewModel(repo) as T
        throw IllegalArgumentException("Unknowns viewModel: ${modelClass.name}")
    }
}