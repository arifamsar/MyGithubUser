package com.arfsar.mygithubuser.utils

import androidx.recyclerview.widget.DiffUtil
import com.arfsar.mygithubuser.data.local.entity.UserFavoriteEntity

class UserFavDiffCallback(
    private val mOldUserFavList: List<UserFavoriteEntity>,
    private val mNewUserFavList: List<UserFavoriteEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldUserFavList.size
    }

    override fun getNewListSize(): Int {
        return mNewUserFavList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserFavList[oldItemPosition].username == mNewUserFavList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUserFavList = mOldUserFavList[oldItemPosition]
        val newUserFavList = mNewUserFavList[newItemPosition]
        return oldUserFavList.username == newUserFavList.username && oldUserFavList.avatarUrl == newUserFavList.avatarUrl
    }

}