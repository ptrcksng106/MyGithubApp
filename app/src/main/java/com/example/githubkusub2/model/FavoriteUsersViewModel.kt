package com.example.githubkusub2.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubkusub2.database.UsersFavorite
import com.example.githubkusub2.repository.UsersFavoriteRepository

class FavoriteUsersViewModel(application: Application) : ViewModel() {

    private val userRepository = UsersFavoriteRepository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getSearchUsers(): LiveData<List<UsersFavorite>> {
        return userRepository.getAllUsersFavorite()
    }

}