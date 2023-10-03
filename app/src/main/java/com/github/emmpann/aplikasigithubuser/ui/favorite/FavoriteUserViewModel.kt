package com.github.emmpann.aplikasigithubuser.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.emmpann.aplikasigithubuser.data.local.database.FavoriteUser
import com.github.emmpann.aplikasigithubuser.data.local.repository.FavoriteUsersRepository

class FavoriteUserViewModel(private val mFavoriteUsersRepository: FavoriteUsersRepository) : ViewModel(){

    init {
        getAllFavoriteUsers()
    }

    fun getAllFavoriteUsers() : LiveData<List<FavoriteUser>> = mFavoriteUsersRepository.getAllFavoriteUsers()
}