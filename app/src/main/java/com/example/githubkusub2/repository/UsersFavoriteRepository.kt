package com.example.githubkusub2.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubkusub2.database.UsersFavorite
import com.example.githubkusub2.database.UsersFavoriteDao
import com.example.githubkusub2.database.UsersFavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UsersFavoriteRepository(application: Application) {
    private val mUsersFavoriteDao: UsersFavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UsersFavoriteRoomDatabase.getDatabase(application)
        mUsersFavoriteDao = db.usersFavoriteDao()
    }

    fun getAllUsersFavorite(): LiveData<List<UsersFavorite>> =
        mUsersFavoriteDao.getAllUsersFavorite()

    fun insert(usersFavorite: UsersFavorite) {
        executorService.execute { mUsersFavoriteDao.insert(usersFavorite) }
    }

    fun delete(usersFavorite: UsersFavorite) {
        executorService.execute { mUsersFavoriteDao.delete(usersFavorite) }
    }

    fun update(usersFavorite: UsersFavorite) {
        executorService.execute { mUsersFavoriteDao.update(usersFavorite) }
    }

    fun checkUsersFavorite(login: String): LiveData<Boolean> {
        val listFavUser = MutableLiveData<Boolean>()

        executorService.execute {
            listFavUser.postValue(mUsersFavoriteDao.checkUsersFavorite(login))
        }
        return listFavUser
    }

}