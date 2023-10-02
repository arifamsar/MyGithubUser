package com.arfsar.mygithubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arfsar.mygithubuser.data.FavoriteRepository
import com.arfsar.mygithubuser.data.local.entity.UserFavoriteEntity
import com.arfsar.mygithubuser.data.response.GithubDetailResponse
import com.arfsar.mygithubuser.data.response.ItemsItem
import com.arfsar.mygithubuser.data.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _user = MutableLiveData<GithubDetailResponse?>()
    val user: MutableLiveData<GithubDetailResponse?> = _user

    private val _followers = MutableLiveData<List<ItemsItem>?>()
    val followers: MutableLiveData<List<ItemsItem>?> = _followers

    private val _followings = MutableLiveData<List<ItemsItem>?>()
    val following: MutableLiveData<List<ItemsItem>?> = _followings

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(user: UserFavoriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            mFavoriteRepository.insert(user)
        }
    }

    fun delete(username: UserFavoriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            mFavoriteRepository.delete(username)
        }
    }

    fun isFavorite(username: String): LiveData<Boolean> {
        return mFavoriteRepository.isFavorite(username)
    }

    fun getUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<GithubDetailResponse> {
            override fun onResponse(
                call: Call<GithubDetailResponse>,
                response: Response<GithubDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _user.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message}")
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followings.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message}")
            }

        })
    }

    fun getFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followers.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

}

