package com.arfsar.mygithubuser.data.retrofit

import com.arfsar.mygithubuser.data.response.GithubDetailResponse
import com.arfsar.mygithubuser.data.response.GithubResponse
import com.arfsar.mygithubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getUsers(): Call<ArrayList<ItemsItem>>

    @GET("search/users")
    fun findUsers(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<GithubDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>
}