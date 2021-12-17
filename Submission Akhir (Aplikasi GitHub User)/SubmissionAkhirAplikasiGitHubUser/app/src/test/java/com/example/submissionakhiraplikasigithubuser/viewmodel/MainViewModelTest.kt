package com.example.submissionakhiraplikasigithubuser.viewmodel

import com.example.submissionakhiraplikasigithubuser.BuildConfig
import com.example.submissionakhiraplikasigithubuser.model.DetailResponse
import com.example.submissionakhiraplikasigithubuser.networking.ApiConfig
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MainViewModelTest {
    private var data: DetailResponse? = null
    private val dummyUsername = "sidiqpermana"

    @Before
    fun setup() {
        data = ApiConfig.getApiService().getDetails(dummyUsername, BuildConfig.API_KEY).execute().body()
    }

    @Test
    fun getDetail() {
        Assert.assertNotNull(data)
        Assert.assertEquals("https://avatars.githubusercontent.com/u/4090245?v=4", data?.avatarUrl)
        Assert.assertEquals("https://github.com/sidiqpermana", data?.htmlUrl)
    }
}