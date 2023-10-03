package com.github.emmpann.aplikasigithubuser.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.github.emmpann.aplikasigithubuser.data.local.preferences.SettingsPreferences
import com.github.emmpann.aplikasigithubuser.data.remote.response.Item
import com.github.emmpann.aplikasigithubuser.data.remote.response.UserResponse
import com.github.emmpann.aplikasigithubuser.data.remote.retrofit.ApiConfig
import com.github.emmpann.aplikasigithubuser.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val pref: SettingsPreferences) : ViewModel() {

    private val _listUser = MutableLiveData<List<Item>>()
    val listUser: LiveData<List<Item>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _statusMessage = MutableLiveData<Event<String>>()
    val statusMessage: LiveData<Event<String>> = _statusMessage

    companion object {
        private const val USER_NAME = "arif"
        private const val TAG = "HomeViewModel"
    }

    init {
        findUser()
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun findUser(username: String = USER_NAME) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.totalCount > 0) {
                            _listUser.value = response.body()?.items
                        } else {
                            _listUser.value = response.body()?.items
                            _statusMessage.value = Event("User not found")
                        }
                    } ?: kotlin.run {
                        _statusMessage.value = Event("An error has occurred")
                    }
                } else {
                    _statusMessage.value = Event(response.message())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                _statusMessage.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}