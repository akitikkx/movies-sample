package com.example.moviessample.data

import com.example.moviessample.data.models.tmdb.toMovie
import com.example.moviessample.data.util.Result
import com.example.moviessample.domain.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TmdbRepositoryImpl @Inject constructor(
    private val tmdbApiService: TmdbApiService
) : TmdbRepository {

    override suspend fun getNowPlayingList(): Flow<Result<List<Movie>>> = flow {
        emit(Result.Loading)
        val result = withContext(Dispatchers.IO) {
            try {
                val nowPlayResponse = tmdbApiService.getNowPlayingList()
                val nowPlayingList: List<Movie> = nowPlayResponse.results.map { details ->
                    details.toMovie()
                }
                Result.Success(nowPlayingList)

            } catch (e: Exception) {
                Result.Failure(e)
            }
        }
        emit(result)
    }.flowOn(Dispatchers.IO)
}