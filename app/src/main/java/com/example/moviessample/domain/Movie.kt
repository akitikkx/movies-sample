package com.example.moviessample.domain

data class Movie(
    val title: String,
    val id: Int? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val tagline: String? = null,
    val imdb: String? = null,
    val slug: String? = null,
    val tmdb: Int? = null,
    val trakt: Int? = null,
)
