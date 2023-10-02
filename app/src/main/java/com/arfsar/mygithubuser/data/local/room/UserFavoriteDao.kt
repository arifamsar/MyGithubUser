package com.arfsar.mygithubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arfsar.mygithubuser.data.local.entity.UserFavoriteEntity

@Dao
interface UserFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(user: UserFavoriteEntity)

    @Delete
    fun removeFavorite(user: UserFavoriteEntity)

    @Query("SELECT * FROM users ORDER BY username ASC")
    fun getAllFavoriteUser(): LiveData<List<UserFavoriteEntity>>

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsername(username: String): UserFavoriteEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE username = :username LIMIT 1)")
    fun isFavorite(username: String): LiveData<Boolean>

}