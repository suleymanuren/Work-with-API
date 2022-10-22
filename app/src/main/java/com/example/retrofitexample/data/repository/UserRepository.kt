package com.example.retrofitexample.data.repository

import com.example.retrofitexample.data.local.database.entity.PostEntity
import com.example.retrofitexample.data.local.database.entity.UserEntity
import com.example.retrofitexample.data.model.Users
import retrofit2.Call

interface UserRepository {
    fun getUsers(): Call<List<Users>>
    fun getUserById(id: Int): UserEntity?
}