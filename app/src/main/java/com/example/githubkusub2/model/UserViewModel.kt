package com.example.githubkusub2.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubkusub2.ItemsItem
import com.example.githubkusub2.UserSearchResponse
import com.example.githubkusub2.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserViewModel() : ViewModel() {

    private val _listUsers = MutableLiveData<List<ItemsItem>>()
    val listUsers: LiveData<List<ItemsItem>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setSearchUser(query: String) {
        ApiConfig.getApiService().findUserSearch(query)
            .enqueue(object : Callback<UserSearchResponse> {
                override fun onResponse(
                    call: Call<UserSearchResponse>,
                    response: Response<UserSearchResponse>
                ) {
                    if (response.isSuccessful) {
                        _listUsers.value = response.body()?.items
                    }
                }

                override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getSearchUsers(): LiveData<List<ItemsItem>> {
        return listUsers
    }

}