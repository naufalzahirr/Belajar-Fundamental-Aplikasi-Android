package com.example.submissionakhiraplikasigithubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionakhiraplikasigithubuser.database.Favorite
import com.example.submissionakhiraplikasigithubuser.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavRepository: FavoriteRepository = FavoriteRepository(application)
    fun getAllFavorites(): LiveData<List<Favorite>> = mFavRepository.getAllFavorites()
}