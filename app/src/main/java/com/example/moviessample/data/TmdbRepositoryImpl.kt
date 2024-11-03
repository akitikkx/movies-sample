package com.example.moviessample.data

import com.example.moviessample.data.util.Result
import com.example.moviessample.domain.Movie
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

    override suspend fun getNowPlayingList(): Flow<Result<List<Movie>>>  = flow {
        emit(Result.Loading)
        val result = withContext(Dispatchers.IO) {
            try {
                val nowPlayResponse = tmdbApiService.getNowPlayingList()
                val nowPlayingList: List<Movie> = nowPlayResponse.results.map { details ->

                    val posterPath = details.poster_path.takeIf { it.isNotEmpty() }?.let {
                        "https://image.tmdb.org/t/p/original$it"
                    }
                    Movie(
                        id = details.id,
                        posterPath = posterPath,
                        overview = details.overview,
                        title = details.title,
                    )
                }
                Result.Success(nowPlayingList)

            } catch (e: Exception) {
                Result.Failure(e)
            }
        }
        emit(result)
    }.flowOn(Dispatchers.IO)
}