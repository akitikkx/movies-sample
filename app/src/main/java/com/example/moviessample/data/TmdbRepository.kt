package com.example.moviessample.data

import com.example.moviessample.domain.MovieDetails

interface TmdbRepository {

    suspend fun getMovieDetails(movieId: Int): Result<MovieDetails>
}