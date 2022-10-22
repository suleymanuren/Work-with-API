package com.example.retrofitexample.data.repository

import com.example.retrofitexample.data.local.database.entity.PostEntity
import com.example.retrofitexample.data.model.Post
import com.example.retrofitexample.data.model.PostDTO
import retrofit2.Call
import retrofit2.Callback


interface PostRepository {

    fun getPosts(): Call<List<Post>>
    fun getPostById(id: Int): Call<Post>
    fun getPostByIdForFavorite(id: Int): PostEntity?
    fun insertFavoritePost(post: PostEntity)
    fun deleteFavoritePost(id: String)
    fun getFavorites(): List<PostEntity>


}