package com.example.examen.rest

import com.example.examen.dataclass.PostResponse
import com.example.examen.dataclass.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

    @GET("profile")
    suspend fun getProfile() :Response<UserResponse>

    @GET("posts")
    suspend fun getPosts() :Response<List<PostResponse>>

    @POST("post/save")
    suspend fun login(@Body user:String, @Body pass:String) : PostResponse


}