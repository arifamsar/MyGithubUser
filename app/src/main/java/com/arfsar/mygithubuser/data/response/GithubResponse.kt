package com.arfsar.mygithubuser.data.response

import com.google.gson.annotations.SerializedName

data class GithubResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: ArrayList<ItemsItem>
)

data class ItemsItem(

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("login")
    val login: String
)
