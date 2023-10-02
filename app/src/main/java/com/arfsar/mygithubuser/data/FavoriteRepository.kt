package com.arfsar.mygithubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.arfsar.mygithubuser.data.local.entity.UserFavoriteEntity
import com.arfsar.mygithubuser.data.local.room.UserFavoriteDao
import com.arfsar.mygithubuser.data.local.room.UserFavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mUserFavDao: UserFavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserFavoriteDatabase.getDatabase(application)
        mUserFavDao = db.userFavoriteDao()
    }

    fun getAllFavoriteUser(): LiveData<List<UserFavoriteEntity>> = mUserFavDao.getAllFavoriteUser()

    fun insert(user: UserFavoriteEntity) {
        executorService.execute { mUserFavDao.insertFavorite(user) }
    }

    fun delete(user: UserFavoriteEntity) {
        executorService.execute { mUserFavDao.removeFavorite(user) }
    }

    fun isFavorite(username: String): LiveData<Boolean> {
        return mUserFavDao.isFavorite(username)
    }
}
