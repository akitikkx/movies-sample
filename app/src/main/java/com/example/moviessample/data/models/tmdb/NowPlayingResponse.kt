package com.example.moviessample.data.models.tmdb

data class NowPlayingResponse(
    val dates: NowPlayingDates,
    val page: Int,
    val results: List<NowPlayingResult>,
    val total_pages: Int,
    val total_results: Int
)