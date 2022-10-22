package com.example.retrofitexample.data.local.database.base

import androidx.room.*
import com.example.retrofitexample.utils.Constants
import retrofit2.http.GET

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(data: List<T>)

    @Update
     fun update(data: T)

    @Delete
     fun delete(data: T)

    @Query("DELETE FROM ${Constants.TABLE_POST_NAME} WHERE postId = :postId")
    fun deleteFavoriteById(postId: String)




}