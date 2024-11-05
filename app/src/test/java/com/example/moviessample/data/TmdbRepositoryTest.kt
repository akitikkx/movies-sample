package com.example.moviessample.data

import com.example.moviessample.data.models.tmdb.NowPlayingDates
import com.example.moviessample.data.models.tmdb.NowPlayingResponse
import com.example.moviessample.data.models.tmdb.NowPlayingResult
import com.example.moviessample.data.models.tmdb.toMovie
import com.example.moviessample.data.util.Result
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TmdbRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var fakeTmdbApiService: FakeTmdbApiService

    private lateinit var repository: TmdbRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeTmdbApiService = FakeTmdbApiService()
        repository = TmdbRepositoryImpl(fakeTmdbApiService)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getNowPlayingList should return success result`() = runTest {
        val results = listOf(
            NowPlayingResult(
                id = 1,
                poster_path = "/poster.jpg",
                overview = "Overview",
                title = "Movie Title"
            )
        )

        val nowPlayingResponse = NowPlayingResponse(
            dates = NowPlayingDates(maximum = "", minimum = ""),
            results = results,
            page = 1,
            total_pages = 1,
            total_results = results.size
        )
        fakeTmdbApiService.setNowPlayingResponse(nowPlayingResponse)

        // Collect all emitted values from the flow into a list
        val emittedValues = repository.getNowPlayingList().take(2).toList()

        // Assert on the last emitted value, which should be Result.Success
        assertEquals(
            Result.Success(nowPlayingResponse.results.map { it.toMovie() }),
            emittedValues.last()
        )
    }
}