package com.example.githubkusub2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UsersFavorite::class], version = 1)
abstract class UsersFavoriteRoomDatabase : RoomDatabase() {
    abstract fun usersFavoriteDao(): UsersFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: UsersFavoriteRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UsersFavoriteRoomDatabase {
            if (INSTANCE == null) {
                synchronized(UsersFavoriteRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UsersFavoriteRoomDatabase::class.java, "UsersFavorite"
                    )
                        .build()
                }
            }

            return INSTANCE as UsersFavoriteRoomDatabase
        }
    }
}