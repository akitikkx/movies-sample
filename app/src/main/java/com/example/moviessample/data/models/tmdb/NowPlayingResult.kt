package com.example.moviessample.data.models.tmdb

import com.example.moviessample.domain.Movie

data class NowPlayingResult(
    val adult: Boolean? = false,
    val backdrop_path: String? = null,
    val genre_ids: List<Int>? = emptyList(),
    val id: Int,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String,
    val popularity: Double? = null,
    val poster_path: String,
    val release_date: String? = null,
    val title: String,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null
)

fun NowPlayingResult.toMovie(): Movie {
    val posterPath = poster_path.takeIf { it.isNotEmpty() }?.let {
        "https://image.tmdb.org/t/p/original$it"
    }

    return Movie(
        id = id,
        posterPath = posterPath,
        overview = overview,
        title = title,
    )
}