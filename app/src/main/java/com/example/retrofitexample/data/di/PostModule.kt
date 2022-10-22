package com.example.retrofitexample.data.di

import com.example.retrofitexample.data.local.database.PostDataBase
import com.example.retrofitexample.data.remote.api.ApiService
import com.example.retrofitexample.data.repository.PostRepository
import com.example.retrofitexample.data.repository.PostRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
@Module
@InstallIn(ViewModelComponent::class)
class PostsModule {
    @Provides
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }
    @Provides
    fun providePostRepository(apiService: ApiService, postsDatabase: PostDataBase) : PostRepository {
        return PostRepositoryImplementation(apiService, postsDatabase)
    }

}