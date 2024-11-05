package com.example.moviessample.data

import com.example.moviessample.data.models.tmdb.NowPlayingDates
import com.example.moviessample.data.models.tmdb.NowPlayingResponse

class FakeTmdbApiService : TmdbApiService {

    private var nowPlayingResponse: NowPlayingResponse? = null

    private var exception: Exception? = null

    fun setNowPlayingResponse(response: NowPlayingResponse) {
        this.nowPlayingResponse = response
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }

    override suspend fun getNowPlayingList(): NowPlayingResponse {
        exception?.let { throw (it) }

        return nowPlayingResponse ?: NowPlayingResponse(
            dates = NowPlayingDates(maximum = "", minimum = ""),
            results = emptyList(),
            page = 1,
            total_pages = 1,
            total_results = 0
        )
    }
}