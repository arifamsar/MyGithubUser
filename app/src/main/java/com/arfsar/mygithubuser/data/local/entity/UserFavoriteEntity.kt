package com.arfsar.mygithubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserFavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    @field:ColumnInfo(name = "username")
    var username: String,

    @field:ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null,

    )