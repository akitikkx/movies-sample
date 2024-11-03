package com.example.moviessample.domain

data class MovieDetails(
    val id: Int,
    val overview: String?,
    val posterPath: String?,
    val title: String,
)

fun MovieDetails.toBoxOfficeMovie(): Movie {
    return Movie(
        id = this.id,
        overview = this.overview,
        posterPath = this.posterPath,
        title = this.title
    )
}
