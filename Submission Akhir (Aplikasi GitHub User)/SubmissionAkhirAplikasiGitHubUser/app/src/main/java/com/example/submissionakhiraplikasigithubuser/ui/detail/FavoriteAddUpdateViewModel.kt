package com.example.submissionakhiraplikasigithubuser.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionakhiraplikasigithubuser.database.Favorite
import com.example.submissionakhiraplikasigithubuser.repository.FavoriteRepository

class FavoriteAddUpdateViewModel(application: Application) : ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }

    fun getFavoriteById(id: Int): LiveData<List<Favorite>> {
        return mFavoriteRepository.getUserFavoriteById(id)
    }

}