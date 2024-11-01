package com.example.moviessample.data

import com.example.moviessample.data.models.tmdb.MovieDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TmdbApiService {

    @GET("movie/{movieId}")
    suspend fun getDetails(
        @Path("movieId") movieId: Int,
    ): MovieDetailsResponse
}