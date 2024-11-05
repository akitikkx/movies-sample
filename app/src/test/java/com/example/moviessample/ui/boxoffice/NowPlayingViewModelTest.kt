package com.example.moviessample.ui.boxoffice

import com.example.moviessample.domain.Movie
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NowPlayingViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: NowPlayingViewModel
    private lateinit var tmdbRepository: FakeTmdbRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        tmdbRepository = FakeTmdbRepository()
        viewModel = NowPlayingViewModel(tmdbRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `uiState should emit Loading state initially`() = runTest {
        val uiStateValues = mutableListOf<NowPlayingUiState>()
        val job = launch {
            viewModel.uiState.collect { nowPlayingUiState ->
                uiStateValues.add(nowPlayingUiState)
            }
        }

        advanceUntilIdle()

        assertEquals(listOf(NowPlayingUiState.Loading), uiStateValues)

        job.cancel()
    }

    @Test
    fun `uiState should emit Success state when repository returns success`() = runTest {
        val movies = listOf(
            Movie(
                id = 2,
                title = "Test Title 2",
                overview = "This is an overview",
                posterPath = "https://www.example.com"
            ),
        )
        tmdbRepository.setMovies(movies)

        val uiStateValues = mutableListOf<NowPlayingUiState>()
        val job = launch {
            viewModel.uiState.collect { nowPlayingUiState ->
                uiStateValues.add(nowPlayingUiState)
            }
        }

        advanceUntilIdle()

        assertEquals(
            listOf(NowPlayingUiState.Loading, NowPlayingUiState.Success(movies)),
            uiStateValues
        )

        job.cancel()
    }

    @Test
    fun `uiState should emit Error state when repository returns failure`() = runTest {
        val exception = Exception("Network error")
        tmdbRepository.setException(exception)

        val uiStateValues = mutableListOf<NowPlayingUiState>()
        val job = launch {
            viewModel.uiState.collect { uiStateValues.add(it) }
        }

        advanceUntilIdle() // Allow flow to emit values

        assertEquals(
            listOf(
                NowPlayingUiState.Loading,
                NowPlayingUiState.Error("An unexpected error occurred: ${exception.message}")
            ),
            uiStateValues
        )

        job.cancel()
    }

    @Test
    fun `uiState should emit Error state when an unexpected exception occurs`() = runTest {
        val exception = Exception("Unexpected error")
        tmdbRepository.setException(exception) // Simulate an exception

        val uiStateValues = mutableListOf<NowPlayingUiState>()
        val job = launch {
            viewModel.uiState.collect { uiStateValues.add(it) }
        }

        advanceUntilIdle() // Allow flow to emit values

        assertEquals(
            listOf(
                NowPlayingUiState.Loading,
                NowPlayingUiState.Error("An unexpected error occurred: ${exception.message}")
            ),
            uiStateValues
        )

        job.cancel()
    }
}