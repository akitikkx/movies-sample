package com.example.moviessample.ui.theme.boxoffice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviessample.data.TmdbRepository
import com.example.moviessample.data.TraktRepository
import com.example.moviessample.data.util.Result
import com.example.moviessample.domain.BoxOfficeMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxOfficeViewModel @Inject constructor(
    private val traktRepository: TraktRepository,
    private val tmdbRepository: TmdbRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BoxOfficeUiState>(BoxOfficeUiState.Loading)
    val uiState: StateFlow<BoxOfficeUiState> = _uiState

    init {
        fetchMovies()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchMovies() {
        viewModelScope.launch {
            _uiState.value = BoxOfficeUiState.Loading

            traktRepository.getBoxOfficeMovies()
                .flatMapConcat { traktResult ->
                    when (traktResult) {
                        is Result.Success -> {
                            val traktMovies = traktResult.data
                            val movieDetailsFlow = traktMovies.map { traktMovie ->
                                tmdbRepository.getMovieDetails(traktMovie.tmdb!!)
                                    .map { tmdbResult ->
                                        when (tmdbResult) {
                                            is Result.Success -> traktMovie.copy(
                                                id = tmdbResult.data.id,
                                                posterPath = tmdbResult.data.posterPath,
                                                tagline = tmdbResult.data.tagline,
                                            )

                                            is Result.Failure -> traktMovie
                                            else -> traktMovie
                                        }
                                    }
                            }
                            combine(movieDetailsFlow) { movieDetailsArray ->
                                movieDetailsArray.toList()
                            }
                        }

                        is Result.Failure -> flow { emit(emptyList<BoxOfficeMovie>()) }
                        else -> flow { emit(emptyList<BoxOfficeMovie>()) }
                    }

                }
                .collect { boxOfficeMovies ->
                    _uiState.value = BoxOfficeUiState.Success(boxOfficeMovies)
                }
        }
    }

}