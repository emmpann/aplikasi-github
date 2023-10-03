package com.github.emmpann.aplikasigithubuser.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.emmpann.aplikasigithubuser.data.local.repository.FavoriteUsersRepository

class DetailViewModelFactory(private val repo: FavoriteUsersRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) return DetailViewModel(repo) as T
        throw IllegalArgumentException("Unknowns viewModel class: ${modelClass.name}")
    }
}