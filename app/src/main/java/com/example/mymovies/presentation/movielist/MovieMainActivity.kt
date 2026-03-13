package com.example.mymovies.presentation.movielist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.mymovies.App
import com.example.mymovies.di.LocalMovieDetailViewModelFactory
import com.example.mymovies.di.LocalViewModelFactory
import com.example.mymovies.presentation.MovieMainScreen
import com.example.mymovies.presentation.SetStatusBarStyle
import com.example.mymovies.presentation.ViewModelFactory
import com.example.mymovies.presentation.detailmovie.MovieDetailViewModel
import com.example.mymovies.presentation.detailmovie.MovieDetailViewModelFactory
import com.example.mymovies.presentation.movielist.ui.theme.MyMoviesTheme
import javax.inject.Inject

class MovieMainActivity : ComponentActivity() {

    val component by lazy {
        (application as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var movieDetailViewModelFactory: MovieDetailViewModel.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CompositionLocalProvider(
                LocalViewModelFactory provides viewModelFactory,
                LocalMovieDetailViewModelFactory provides movieDetailViewModelFactory
            ) {
                MyMoviesTheme {
                    SetStatusBarStyle()
                    MovieMainScreen(viewModelFactory)
                }
            }
        }
    }
}
