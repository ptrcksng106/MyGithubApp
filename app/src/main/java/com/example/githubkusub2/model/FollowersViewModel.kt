package com.example.githubkusub2.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubkusub2.ItemsItem
import com.example.githubkusub2.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    private val _listFollowers = MutableLiveData<List<ItemsItem>>()
    private val listFollowers: LiveData<List<ItemsItem>> = _listFollowers

    fun setListFollowersUser(username: String) {
        ApiConfig.getApiService().getFollowers(username)
            .enqueue(object : Callback<List<ItemsItem>> {
                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
                ) {
                    if (response.isSuccessful) {
                        _listFollowers.value = response.body()
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getListFollowersUser(): LiveData<List<ItemsItem>> {
        return listFollowers
    }
}