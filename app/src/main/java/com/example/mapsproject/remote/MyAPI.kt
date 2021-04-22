package com.example.mapsproject.remote

import com.example.mapsproject.model.APIResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyAPI {

    @FormUrlEncoded
    @POST("register.php")
    fun registerUser(@Field("login") login: String,@Field("pass") pass: String,@Field("pass2") pass2: String):retrofit2.Call<APIResponse>

    @FormUrlEncoded
    @POST("login.php")
    fun loginUser(@Field("login") login: String,@Field("pass") pass: String):retrofit2.Call<APIResponse>
}