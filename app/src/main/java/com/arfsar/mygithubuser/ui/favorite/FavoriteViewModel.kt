package com.arfsar.mygithubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arfsar.mygithubuser.data.FavoriteRepository
import com.arfsar.mygithubuser.data.local.entity.UserFavoriteEntity

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavoriteUser(): LiveData<List<UserFavoriteEntity>> =
        mFavoriteRepository.getAllFavoriteUser()
}