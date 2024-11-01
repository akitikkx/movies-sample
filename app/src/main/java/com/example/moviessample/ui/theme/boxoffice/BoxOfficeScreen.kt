package com.example.moviessample.ui.theme.boxoffice

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
import com.example.moviessample.domain.BoxOfficeMovie

@Composable
fun BoxOfficeScreen(
    viewModel: BoxOfficeViewModel = hiltViewModel()
) {
    val uiState: BoxOfficeUiState by viewModel.uiState.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (uiState) {
                is BoxOfficeUiState.Loading -> {
                    LoadingScreen()
                }

                is BoxOfficeUiState.Success -> {
                    BoxOfficesMovies(
                        list = (uiState as BoxOfficeUiState.Success).movies,
                    )
                }

                is BoxOfficeUiState.Error -> {
                    ErrorScreen(message = (uiState as BoxOfficeUiState.Error).message)
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
fun BoxOfficesMovies(
    list: List<BoxOfficeMovie>
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(list) { movie ->
                BoxOfficeCard(movie = movie)
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