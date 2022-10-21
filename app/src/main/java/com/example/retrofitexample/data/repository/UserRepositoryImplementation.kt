package com.example.retrofitexample.data.repository

import com.example.retrofitexample.data.local.database.PostDataBase
import com.example.retrofitexample.data.local.database.UserDataBase
import com.example.retrofitexample.data.local.database.entity.PostEntity
import com.example.retrofitexample.data.local.database.entity.UserEntity
import com.example.retrofitexample.data.remote.api.ApiService
import com.example.retrofitexample.data.model.Post
import com.example.retrofitexample.data.model.UserDTO
import com.example.retrofitexample.data.model.Users
import retrofit2.Call

class UserRepositoryImplementation constructor(
    private val apiService: ApiService,
    private val usersDatabase: UserDataBase
) : UserRepository {
    override fun getUsers(): Call<List<Users>> {
        return apiService.getUsers()
    }

    override fun getUserById(id: Int): UserEntity? {
        return usersDatabase.userDao().getUserById(id.toString())
    }

}