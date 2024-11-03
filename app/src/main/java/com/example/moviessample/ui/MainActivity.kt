package com.example.moviessample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.moviessample.ui.theme.MoviesSampleTheme
import com.example.moviessample.ui.boxoffice.NowPlayingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MoviesSampleTheme {
                NowPlayingScreen()
            }
        }
    }
}