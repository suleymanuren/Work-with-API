package com.example.retrofitexample.data.repository

import com.example.retrofitexample.data.local.database.PostDataBase
import com.example.retrofitexample.data.local.database.entity.PostEntity
import com.example.retrofitexample.data.remote.api.ApiService
import com.example.retrofitexample.data.model.Post
import com.example.retrofitexample.data.model.Users
import retrofit2.Call

class PostRepositoryImplementation constructor(
    private val apiService: ApiService,
    private val postsDatabase: PostDataBase
) : PostRepository {
    override fun getPosts(): Call<List<Post>> {
        return apiService.getPosts()
    }


    override fun getPostById(id: Int): PostEntity? {
        return postsDatabase.postDao().getPostById(id.toString())
    }

    override fun insertFavoritePost(post: PostEntity) {
        return postsDatabase.postDao().insert(post)
    }

    override fun deleteFavoritePost(post: PostEntity) {
        return postsDatabase.postDao().delete(post)
    }
}