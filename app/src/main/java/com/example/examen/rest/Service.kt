package com.example.examen.rest

import com.example.examen.dataclass.PostResponse
import com.example.examen.dataclass.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface Service {

    @GET("profile")
    suspend fun getProfile() :Response<UserResponse>

    @GET("posts")
    suspend fun getPosts() :Response<List<PostResponse>>

    @GET("users")
    suspend fun getUsers() :Response<List<UserResponse>>

}