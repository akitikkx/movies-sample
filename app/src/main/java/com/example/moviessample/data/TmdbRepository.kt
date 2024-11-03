package com.example.moviessample.data

import com.example.moviessample.data.util.Result
import com.example.moviessample.domain.Movie
import kotlinx.coroutines.flow.Flow

interface TmdbRepository {

    suspend fun getNowPlayingList(): Flow<Result<List<Movie>>>
}