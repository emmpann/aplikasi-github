package com.github.emmpann.aplikasigithubuser.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.github.emmpann.aplikasigithubuser.data.local.database.FavoriteUserDao
import com.github.emmpann.aplikasigithubuser.data.local.database.FavoriteUserRoomDatabase
import com.github.emmpann.aplikasigithubuser.data.local.database.FavoriteUser

class FavoriteUsersRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    suspend fun insert(favoriteUser: FavoriteUser) {
         mFavoriteUserDao.insert(favoriteUser)
    }

    suspend fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserDao.delete(favoriteUser)
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavoriteUsers()

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = mFavoriteUserDao.getFavoriteUserByUsername(username)
}