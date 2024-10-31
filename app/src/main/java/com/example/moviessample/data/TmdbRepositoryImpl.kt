package com.example.moviessample.data

import com.example.moviessample.data.models.tmdb.MovieDetailsResponse
import com.example.moviessample.domain.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TmdbRepositoryImpl @Inject constructor(
    private val tmdbApiService: TmdbApiService
) : TmdbRepository {

    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetails> {
        return try {
            val result = withContext(Dispatchers.IO) {
                tmdbApiService.getDetails(
                    movieId = movieId,
                    apiKey = ""
                )
            }
            result.map { response ->
                MovieDetails(
                    id = response.id,
                    posterPath = response.poster_path,
                    overview =  response.overview,
                    tagline = response.tagline
                )
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}