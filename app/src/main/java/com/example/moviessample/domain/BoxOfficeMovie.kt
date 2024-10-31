package com.example.moviessample.domain

data class BoxOfficeMovie(
    val id: Int,
    val title: String,
    val year: Int,
    val overview: String? = null,
    val posterPath: String? = null,
    val tagline: String? = null,
)
