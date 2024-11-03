package com.example.moviessample.ui.boxoffice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviessample.domain.Movie

@Composable
fun NowPlayingScreen(
    viewModel: NowPlayingViewModel = hiltViewModel()
) {
    val uiState: NowPlayingUiState by viewModel.uiState.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (uiState) {
                is NowPlayingUiState.Loading -> {
                    LoadingScreen()
                }

                is NowPlayingUiState.Success -> {
                    NowPlayingMovies(
                        list = (uiState as NowPlayingUiState.Success).movies,
                    )
                }

                is NowPlayingUiState.Error -> {
                    ErrorScreen(message = (uiState as NowPlayingUiState.Error).message)
                }
            }
        }
    }


}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun NowPlayingMovies(
    list: List<Movie>
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        content = {
            items(list) { movie ->
                NowPlayingItemCard(movie = movie)
            }
        }
    )
}

@Composable
fun ErrorScreen(message: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = message)
    }
}