package com.example.moviessample.ui.theme.boxoffice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviessample.R
import com.example.moviessample.domain.BoxOfficeMovie
import com.example.moviessample.ui.theme.MoviesSampleTheme

@Composable
fun BoxOfficeCard(movie: BoxOfficeMovie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.posterPath)
                    .error(R.drawable.poster_placeholder)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = movie.title,
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = movie.title)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoxOfficeCardPreview() {
    MoviesSampleTheme {
        BoxOfficeCard(
            movie = BoxOfficeMovie(
                title = "Movie Title",
                year = 2024,
                posterPath = "https://image.tmdb.org/t/p/original/k42Owka8v91trK1qMYwCQCNwJKr.jpg"
            )
        )
    }
}