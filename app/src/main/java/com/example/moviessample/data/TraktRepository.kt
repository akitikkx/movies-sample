package com.example.moviessample.data

import com.example.moviessample.data.util.Result
import com.example.moviessample.domain.BoxOfficeMovie
import kotlinx.coroutines.flow.Flow

interface TraktRepository {

    suspend fun getBoxOfficeMovies(): Flow<Result<List<BoxOfficeMovie>>>
}