package com.example.moviessample.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("movie/{movieId}")
    suspend fun getDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    )
}