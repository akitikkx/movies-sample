package com.example.moviessample.ui.boxoffice

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
}