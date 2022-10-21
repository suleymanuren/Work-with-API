package com.example.retrofitexample.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.retrofitexample.data.local.database.base.BaseDao
import com.example.retrofitexample.data.local.database.entity.PostEntity
import com.example.retrofitexample.data.local.database.entity.UserEntity
import com.example.retrofitexample.utils.Constants

@Dao
interface UserDao : BaseDao<UserEntity> {


    @Query("SELECT * FROM ${Constants.TABLE_USER_NAME}")
    fun getAllUser(): List<UserEntity>

    @Query("DELETE FROM ${Constants.TABLE_USER_NAME}")
    fun deleteAllUser()

    @Query("SELECT * FROM ${Constants.TABLE_USER_NAME} WHERE userId = :userId")
    fun getUserById(userId: String): UserEntity?


}