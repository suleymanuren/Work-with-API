package com.example.retrofitexample.data.repository

import com.example.retrofitexample.data.local.database.PostDataBase
import com.example.retrofitexample.data.local.database.entity.PostEntity
import com.example.retrofitexample.data.remote.api.ApiService
import com.example.retrofitexample.data.model.Post
import com.example.retrofitexample.data.model.PostDTO
import retrofit2.Call
import retrofit2.Callback

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

    override fun deleteFavoritePost(id: String) {
        return postsDatabase.postDao().deleteFavoriteById(id)
    }


    override fun getFavorites(): List<PostEntity> {
        return postsDatabase.postDao().getAllPosts()
    }

    override fun getPostByIdForFavorite(id: Int): PostEntity? {
        return postsDatabase.postDao().getPostById(id.toString())
    }


}