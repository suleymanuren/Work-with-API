package com.example.retrofitexample.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retrofitexample.data.local.database.dao.UserDao
import com.example.retrofitexample.data.local.database.entity.UserEntity
import com.example.retrofitexample.utils.Constants

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var instance: UserDataBase? = null

        fun getUserDatabase(context: Context): UserDataBase = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, UserDataBase::class.java, Constants.TABLE_USER_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}