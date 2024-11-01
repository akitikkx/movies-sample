package com.example.moviessample.data

import com.example.moviessample.data.util.Result
import com.example.moviessample.domain.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TmdbRepositoryImpl @Inject constructor(
    private val tmdbApiService: TmdbApiService
) : TmdbRepository {

    override suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieDetails>> = flow {
        emit(Result.Loading)
        val result = withContext(Dispatchers.IO) {
            try {
                val details = tmdbApiService.getDetails(movieId)
                val movieDetails = MovieDetails(
                    id = details.id,
                    posterPath = details.poster_path,
                    overview = details.overview,
                    tagline = details.tagline
                )
                Result.Success(movieDetails)
            } catch (e: Exception) {
                Result.Failure(e)
            }
        }
        emit(result)
    }.flowOn(Dispatchers.IO)
}