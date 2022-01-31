package com.example.gravitytesttask.data.remote

import retrofit2.http.GET


interface Api {
    @GET("/prod")
    suspend fun getResponse(): ApiResponse
}