package com.example.moviessample.data

import com.example.moviessample.data.util.Result
import com.example.moviessample.domain.BoxOfficeMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TraktRepositoryImpl @Inject constructor(
    private val traktApiService: TraktApiService
) : TraktRepository {

    override suspend fun getBoxOfficeMovies(): Flow<Result<List<BoxOfficeMovie>>> = flow {
        emit(Result.Loading)
        val result = withContext(Dispatchers.IO) {
            try {
                val movies = traktApiService.getBoxOfficeMovies()
                    .map { item ->
                        BoxOfficeMovie(
                            title = item.movie.title,
                            year = item.movie.year,
                            imdb = item.movie.ids.imdb,
                            slug = item.movie.ids.slug,
                            tmdb = item.movie.ids.tmdb,
                            trakt = item.movie.ids.trakt
                        )
                    }
                Result.Success(movies) // Wrap the result in Success
            } catch (e: Exception) {
                Result.Failure(e) // Wrap the exception in Failure
            }
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

}