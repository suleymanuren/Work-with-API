package com.example.retrofitexample.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.retrofitexample.utils.Constants

@Entity(tableName = Constants.TABLE_USER_NAME)
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long? = null,
    @ColumnInfo(name = "userId") val userId: String?,
    @ColumnInfo(name = "userName") val userName: String?,
    @ColumnInfo(name = "userUsername") val userUsername: String?,
    @ColumnInfo(name = "userEmail") val userEmail: String?,
    @ColumnInfo(name = "userPhone") val userPhone: String?,
    @ColumnInfo(name = "userWebsite") val userWebsite: String?,
    @ColumnInfo(name = "userAdress") val userAdress: String?,
    @ColumnInfo(name = "userCompany") val userCompany: String?,


    )

