package com.github.emmpann.aplikasigithubuser.data.remote.retrofit

import com.github.emmpann.aplikasigithubuser.data.remote.response.Item
import com.github.emmpann.aplikasigithubuser.data.remote.response.UserDetailResponse
import com.github.emmpann.aplikasigithubuser.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    fun getUser(
        @Query("q") id: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers") // https://api.github.com/users/sidiqpermana/followers
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<Item>>

    @GET("users/{username}/following")  // https://api.github.com/users/sidiqpermana/following

    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<Item>>
}