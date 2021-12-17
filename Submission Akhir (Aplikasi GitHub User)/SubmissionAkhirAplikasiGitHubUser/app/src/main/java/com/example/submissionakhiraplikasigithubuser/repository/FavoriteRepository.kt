package com.example.submissionakhiraplikasigithubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submissionakhiraplikasigithubuser.database.Favorite
import com.example.submissionakhiraplikasigithubuser.database.FavoriteDao
import com.example.submissionakhiraplikasigithubuser.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoritesDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoritesDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoritesDao.getAllFavorites()
    fun insert(favorite: Favorite) {
        executorService.execute { mFavoritesDao.insert(favorite) }
    }

    fun delete(favorite: Favorite) {
        executorService.execute { mFavoritesDao.delete(favorite) }
    }

    fun getUserFavoriteById(id: Int): LiveData<List<Favorite>> =
        mFavoritesDao.getUserFavoriteById(id)
}