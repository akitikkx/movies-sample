package com.example.moviessample.ui.boxoffice

import com.example.moviessample.domain.Movie

sealed interface NowPlayingUiState {
    object Loading : NowPlayingUiState
    data class Success(val movies: List<Movie>) : NowPlayingUiState
    data class Error(val message: String) : NowPlayingUiState
}
