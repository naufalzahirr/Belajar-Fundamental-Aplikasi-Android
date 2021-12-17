package com.example.submission2aplikasigithubusernavigationdanapi.networking

import com.example.submission2aplikasigithubusernavigationdanapi.model.DetailResponse
import com.example.submission2aplikasigithubusernavigationdanapi.model.FollowersResponseItem
import com.example.submission2aplikasigithubusernavigationdanapi.model.FollowingResponseItem
import com.example.submission2aplikasigithubusernavigationdanapi.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users?")
    fun getUser(
        @Query("q") q: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetails(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<FollowersResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<FollowingResponseItem>>

}