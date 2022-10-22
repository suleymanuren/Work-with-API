package com.example.retrofitexample.data.repository

import com.example.retrofitexample.data.local.database.PostDataBase
import com.example.retrofitexample.data.local.database.entity.PostEntity
import com.example.retrofitexample.data.remote.api.ApiService
import com.example.retrofitexample.data.model.Post
import com.example.retrofitexample.data.model.PostDTO
import com.example.retrofitexample.data.model.Users
import retrofit2.Call

class PostRepositoryImplementation constructor(
    private val apiService: ApiService,
    private val postsDatabase: PostDataBase
) : PostRepository {
    override fun getPosts(): Call<List<Post>> {
        return apiService.getPosts()
    }


    override fun getPostById(id: Int): Call<Post> {
        return apiService.getPostById(id)
    }

    override fun insertFavoritePost(post: PostEntity) {
        return postsDatabase.postDao().insert(post)
    }

    override fun deleteFavoritePost(post: PostEntity) {
        return postsDatabase.postDao().delete(post)
    }




}