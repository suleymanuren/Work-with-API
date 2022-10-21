package com.example.retrofitexample.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retrofitexample.data.local.database.dao.PostDao
import com.example.retrofitexample.data.local.database.entity.PostEntity
import com.example.retrofitexample.utils.Constants

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
abstract class PostDataBase : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        private var instance: PostDataBase? = null

        fun getDatabase(context: Context):PostDataBase = instance ?: synchronized(this) {
            instance ?: buildUserDatabase(context).also { instance = it }
        }

      private fun buildUserDatabase(context: Context) =
           Room.databaseBuilder(context, PostDataBase::class.java, Constants.TABLE_NAME)
          .fallbackToDestructiveMigration()
          .allowMainThreadQueries()
          .build()
    }
}