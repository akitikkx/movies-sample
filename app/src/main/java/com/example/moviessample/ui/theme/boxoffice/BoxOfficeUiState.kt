package com.example.moviessample.ui.theme.boxoffice

import com.example.moviessample.domain.BoxOfficeMovie

sealed interface BoxOfficeUiState {
    object Loading : BoxOfficeUiState
    data class Success(val movies: List<BoxOfficeMovie>) : BoxOfficeUiState
    data class Error(val message: String) : BoxOfficeUiState
}
