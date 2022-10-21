package com.example.retrofitexample.data.remote.api

import com.example.retrofitexample.data.model.Post
import com.example.retrofitexample.data.model.Users
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @GET("users")
    fun getUsers(): Call<List<Users>>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id: Int): Call<Post>

    @GET("posts/{id}")
    fun getPostById(@Path("id") id: Int): Call<Post>
}