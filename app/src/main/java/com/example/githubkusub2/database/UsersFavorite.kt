package com.example.githubkusub2.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "UsersFavorite")
@Parcelize
data class UsersFavorite(
    @PrimaryKey
    val login: String,

    val avatar_url: String
) : Parcelable
