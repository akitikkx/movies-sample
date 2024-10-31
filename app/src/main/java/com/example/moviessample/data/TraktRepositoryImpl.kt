package com.example.moviessample.data

import com.example.moviessample.domain.BoxOfficeMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TraktRepositoryImpl @Inject constructor(
    private val traktApiService: TraktApiService
) : TraktRepository {

    override suspend fun getBoxOfficeMovies(): Result<List<BoxOfficeMovie>> {
        return try {
            val result = withContext(Dispatchers.IO) { traktApiService.getBoxOfficeMovies() }

            result.map { boxOfficeResponse ->
                boxOfficeResponse.map { item ->
                    BoxOfficeMovie(
                        title = item.movie.title,
                        year = item.movie.year
                    )
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}