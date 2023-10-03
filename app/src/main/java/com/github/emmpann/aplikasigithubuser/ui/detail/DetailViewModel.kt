package com.github.emmpann.aplikasigithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.aplikasigithubuser.data.local.database.FavoriteUser
import com.github.emmpann.aplikasigithubuser.data.local.repository.FavoriteUsersRepository
import com.github.emmpann.aplikasigithubuser.data.remote.response.UserDetailResponse
import com.github.emmpann.aplikasigithubuser.data.remote.retrofit.ApiConfig
import com.github.emmpann.aplikasigithubuser.util.Event
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val mFavoriteUserRepository: FavoriteUsersRepository) : ViewModel(){

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _userDetail = MutableLiveData<UserDetailResponse>()
    val userDetail: LiveData<UserDetailResponse> = _userDetail

    private val _statusMessage = MutableLiveData<Event<String>>()
    val statusMessage: LiveData<Event<String>> = _statusMessage

    init {
        getUserDetail()
    }

    fun addFavoriteUser(favoriteUser: FavoriteUser) {
        viewModelScope.launch {
            mFavoriteUserRepository.insert(favoriteUser)
        }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = mFavoriteUserRepository.getFavoriteUserByUsername(username)

    fun deleteFavoriteUser(favUser: FavoriteUser) {
        viewModelScope.launch {
            mFavoriteUserRepository.delete(favUser)
        }
    }

    private fun getUserDetail() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(DetailFragment.USERNAME)

        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}