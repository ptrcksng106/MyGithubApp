package com.example.githubkusub2.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubkusub2.UserDetailResponse
import com.example.githubkusub2.api.ApiConfig
import com.example.githubkusub2.repository.UsersFavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : ViewModel() {

    private val userRepository = UsersFavoriteRepository(application)

    private val _user = MutableLiveData<UserDetailResponse>()
    private val user: LiveData<UserDetailResponse> = _user

    fun setUserDetail(username: String) {
        ApiConfig.getApiService().getUserDetail(username)
            .enqueue(object : Callback<UserDetailResponse> {
                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        _user.value = response.body()
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getUserDetail(): LiveData<UserDetailResponse> {
        return user
    }

    fun checkUserFavorite(login: String): LiveData<Boolean> {
        return userRepository.checkUsersFavorite(login)
    }
}