package com.example.moviessample.data

import com.example.moviessample.data.models.trakt.BoxOfficeResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface TraktApiService {

    @GET("movies/boxoffice")
    suspend fun getBoxOfficeMovies(): Deferred<BoxOfficeResponse>
}