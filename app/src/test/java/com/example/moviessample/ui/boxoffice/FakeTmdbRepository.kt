package com.example.moviessample.ui.boxoffice

import com.example.moviessample.data.TmdbRepository
import com.example.moviessample.data.util.Result
import com.example.moviessample.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTmdbRepository : TmdbRepository {

    private var movies: List<Movie>? = null
    private var exception: Exception? = null

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }

    private var fakeResult: Result<List<Movie>> = Result.Success(emptyList())

    fun setFakeResult(result: Result<List<Movie>>) {
        this.fakeResult = result
    }

    override suspend fun getNowPlayingList(): Flow<Result<List<Movie>>> = flow {
        emit(Result.Loading)
        exception?.let { throw it }
        movies?.let { emit(Result.Success(it)) }
    }
}