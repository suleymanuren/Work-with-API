package com.example.retrofitexample.data.di

import android.content.Context
import com.example.retrofitexample.data.local.database.UserDataBase
import com.example.retrofitexample.data.local.database.PostDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) : PostDataBase {
        return PostDataBase.getDatabase(appContext)
    }
    @Singleton
    @Provides
    fun providePostDao(db: PostDataBase) = db.postDao()

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext appContext: Context) : UserDataBase {
        return UserDataBase.getUserDatabase(appContext)
    }

    @Singleton
    @Provides
    fun provideUserDao(db: UserDataBase) = db.userDao()
}