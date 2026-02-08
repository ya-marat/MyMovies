package com.example.mymovies.presentation.detailmovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.repeatOnLifecycle
import com.example.mymovies.App
import com.example.mymovies.domain.Movie
import com.example.mymovies.empty
import com.example.mymovies.presentation.AppScaffold
import com.example.mymovies.presentation.SetStatusBarStyle
import com.example.mymovies.presentation.ViewModelFactory
import com.example.mymovies.presentation.detailmovie.ui.theme.MyMoviesTheme
import javax.inject.Inject

class MovieDetailActivity : ComponentActivity() {

    val component by lazy {
        (application as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, Movie.UNDEFINED_ID)
        val movieName = intent.getStringExtra(EXTRA_MOVIE_NAME)

        val viewModel = ViewModelProvider(this, viewModelFactory)[MovieDetailViewModel::class.java]

        viewModel.loadMovieById(movieId)

        setContent {
            MyMoviesTheme {

                SetStatusBarStyle(
                    Color.Black,
                    false
                )

                AppScaffold(
                    title = movieName ?: String.empty(),
                    onBackClick = { finish() }
                ) {
                    DetailMovieScreen(viewModel = viewModel)
                }
            }
        }
    }

    companion object {

        private const val EXTRA_MOVIE_NAME = "movie_name"
        private const val EXTRA_MOVIE_ID = "movie_id"

        fun newIntent(context: Context, movieName: String, movieId: Int): Intent {
            val newIntent = Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_NAME, movieName)
                putExtra(EXTRA_MOVIE_ID, movieId)
            }

            return newIntent
        }
    }
}
