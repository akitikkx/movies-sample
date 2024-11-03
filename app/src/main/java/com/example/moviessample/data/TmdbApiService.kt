package com.example.moviessample.data

import com.example.moviessample.data.models.tmdb.NowPlayingResponse
import retrofit2.http.GET

interface TmdbApiService {

    @GET("discover/movie")
    suspend fun getNowPlayingList(): NowPlayingResponse
}