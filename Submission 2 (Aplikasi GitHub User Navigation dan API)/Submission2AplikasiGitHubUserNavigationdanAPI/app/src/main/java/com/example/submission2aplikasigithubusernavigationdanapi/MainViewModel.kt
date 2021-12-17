package com.example.submission2aplikasigithubusernavigationdanapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2aplikasigithubusernavigationdanapi.model.*
import com.example.submission2aplikasigithubusernavigationdanapi.networking.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : ViewModel() {

    private val _user = MutableLiveData<List<ItemsItem>>()
    val user: LiveData<List<ItemsItem>> = _user

    private val _detail = MutableLiveData<DetailResponse>()
    val detail: LiveData<DetailResponse> = _detail

    private val _followers = MutableLiveData<List<FollowersResponseItem>>()
    val followers: LiveData<List<FollowersResponseItem>> = _followers

    private val _following = MutableLiveData<List<FollowingResponseItem>>()
    val following: LiveData<List<FollowingResponseItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
    }

    fun findUser(USERNAME : String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(USERNAME ?: "")
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.items

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun findDetail(USERNAME : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetails(USERNAME)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>

            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detail.value = response.body()?.copy()

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun findFollowers(USERNAME : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(USERNAME)
        client.enqueue(object : Callback<List<FollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersResponseItem>>,
                response: Response<List<FollowersResponseItem>>
            ) {

                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun findFollowing(USERNAME : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(USERNAME)
        client.enqueue(object : Callback<List<FollowingResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowingResponseItem>>,
                response: Response<List<FollowingResponseItem>>
            ) {

                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}