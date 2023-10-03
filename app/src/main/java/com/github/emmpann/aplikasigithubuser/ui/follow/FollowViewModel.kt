package com.github.emmpann.aplikasigithubuser.ui.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.emmpann.aplikasigithubuser.data.remote.response.Item
import com.github.emmpann.aplikasigithubuser.data.remote.retrofit.ApiConfig
import com.github.emmpann.aplikasigithubuser.ui.detail.DetailFragment
import com.github.emmpann.aplikasigithubuser.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _listFollowers = MutableLiveData<List<Item>>()
    val listFollowers: LiveData<List<Item>> = _listFollowers

    private var _listFollowing = MutableLiveData<List<Item>>()
    val listFollowing: LiveData<List<Item>> = _listFollowing

    private val _statusMessage = MutableLiveData<Event<String>>()

    init {
        getFollowers()
        getFollowing()
    }

    private fun getFollowers() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(DetailFragment.USERNAME)
        Log.d("getFollowers", DetailFragment.USERNAME)
        client.enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _listFollowers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    private fun getFollowing() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(DetailFragment.USERNAME)
        Log.d("getFollowing", DetailFragment.USERNAME)
        client.enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    _statusMessage.value = Event(response.message())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                _isLoading.value = false
                _statusMessage.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        const val TAG = "FollowViewModel"
    }
}