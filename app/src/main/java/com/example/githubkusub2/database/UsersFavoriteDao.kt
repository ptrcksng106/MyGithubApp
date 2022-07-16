package com.example.githubkusub2.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UsersFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(usersFavorite: UsersFavorite)

    @Update
    fun update(usersFavorite: UsersFavorite)

    @Delete
    fun delete(usersFavorite: UsersFavorite)

    @Query("SELECT * from UsersFavorite ORDER BY login ASC")
    fun getAllUsersFavorite(): LiveData<List<UsersFavorite>>

    @Query("SELECT EXISTS(SELECT  login from UsersFavorite WHERE usersfavorite.login = :login)")
    fun checkUsersFavorite(login: String): Boolean
}