package com.example.retrofitexample.data.di

import com.example.retrofitexample.data.local.database.PostDataBase
import com.example.retrofitexample.data.local.database.UserDataBase
import com.example.retrofitexample.data.remote.api.ApiService
import com.example.retrofitexample.data.repository.PostRepository
import com.example.retrofitexample.data.repository.PostRepositoryImplementation


import com.example.retrofitexample.data.repository.UserRepository
import com.example.retrofitexample.data.repository.UserRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
class UserModule {


    @Provides
    fun provideUserRepository(apiService: ApiService, userDataNase: UserDataBase) : UserRepository {
        return UserRepositoryImplementation(apiService, userDataNase)
    }
}