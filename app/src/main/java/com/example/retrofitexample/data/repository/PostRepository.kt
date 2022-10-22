package com.example.retrofitexample.data.repository

import com.example.retrofitexample.data.local.database.entity.PostEntity
import com.example.retrofitexample.data.model.Post
import com.example.retrofitexample.data.model.PostDTO
import retrofit2.Call


interface PostRepository {

    fun getPosts(): Call<List<Post>>
    fun getPostById(id: Int): Call<Post>
    fun insertFavoritePost(post: PostEntity)
    fun deleteFavoritePost(post: PostEntity)



}