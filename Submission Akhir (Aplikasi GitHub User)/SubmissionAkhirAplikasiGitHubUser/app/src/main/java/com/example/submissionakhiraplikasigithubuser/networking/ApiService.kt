package com.example.submissionakhiraplikasigithubuser.networking

import com.example.submissionakhiraplikasigithubuser.model.DetailResponse
import com.example.submissionakhiraplikasigithubuser.model.FollowersResponseItem
import com.example.submissionakhiraplikasigithubuser.model.FollowingResponseItem
import com.example.submissionakhiraplikasigithubuser.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users?")
    fun getUser(
        @Query("q") q: String,
        @Header("Authorization") token: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetails(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<List<FollowersResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<List<FollowingResponseItem>>

}