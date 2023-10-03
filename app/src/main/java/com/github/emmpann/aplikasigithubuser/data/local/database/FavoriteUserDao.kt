package com.github.emmpann.aplikasigithubuser.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteUser: FavoriteUser)

    @Update
    fun update(favoriteUser: FavoriteUser)

    @Delete
    suspend fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favoriteuser ORDER BY id ASC")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM favoriteuser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>
}