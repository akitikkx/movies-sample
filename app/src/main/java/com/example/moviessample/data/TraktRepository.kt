package com.example.moviessample.data

import com.example.moviessample.domain.BoxOfficeMovie

interface TraktRepository {

    suspend fun getBoxOfficeMovies(): Result<List<BoxOfficeMovie>>
}