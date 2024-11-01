package com.example.moviessample.data

import com.example.moviessample.data.util.Result
import com.example.moviessample.domain.MovieDetails
import kotlinx.coroutines.flow.Flow

interface TmdbRepository {

    suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieDetails>>
}