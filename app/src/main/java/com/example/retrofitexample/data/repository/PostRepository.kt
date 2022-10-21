package com.example.retrofitexample.data.repository

import com.example.retrofitexample.data.local.database.entity.PostEntity
import com.example.retrofitexample.data.model.Post
import retrofit2.Call


interface PostRepository {

    fun getPosts(): Call<List<Post>>
    fun getPostById(id: Int): PostEntity?
    fun insertFavoritePost(post: PostEntity)
    fun deleteFavoritePost(post: PostEntity)


}