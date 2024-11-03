package com.example.moviessample.ui.boxoffice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviessample.data.TmdbRepository
import com.example.moviessample.data.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val tmdbRepository: TmdbRepository
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<NowPlayingUiState> = flow {
        emit(tmdbRepository.getNowPlayingList())
    }
        // flatMapConcat used to "unwrap" the flow emitted from their inner block and continue with
        // the rest of the transformations
        .flatMapConcat { nowPlayingFlow ->
            nowPlayingFlow
                .map { result ->
                    when (result) {
                        is Result.Loading -> NowPlayingUiState.Loading

                        is Result.Success -> NowPlayingUiState.Success(result.data.map { it })

                        is Result.Failure -> NowPlayingUiState.Error(
                            "Failed to fetch movies: " +
                                    "${result.exception.message}"
                        )
                    }
                }
                .catch { exception ->
                    emit(NowPlayingUiState.Error("An unexpected error occurred: ${exception.message}"))
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NowPlayingUiState.Loading
        )
}