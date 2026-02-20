package com.example.mymovies.presentation.movielist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mymovies.App
import com.example.mymovies.presentation.MovieMainScreen
import com.example.mymovies.presentation.SetStatusBarStyle
import com.example.mymovies.presentation.ViewModelFactory
import com.example.mymovies.presentation.detailmovie.MovieDetailActivity
import com.example.mymovies.presentation.favourites.FavouriteMoviesViewModel
import com.example.mymovies.presentation.favourites.MovieFavouriteScreen
import com.example.mymovies.presentation.movielist.ui.theme.MyMoviesTheme
import javax.inject.Inject

class MovieMainActivity : ComponentActivity() {

    val component by lazy {
        (application as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        val movieListViewModel =
            ViewModelProvider(this, viewModelFactory)[MovieListViewModel::class.java]
        val favouritesViewModel =
            ViewModelProvider(this, viewModelFactory)[FavouriteMoviesViewModel::class.java]

        setContent {
            MyMoviesTheme {

                SetStatusBarStyle()

                MovieMainScreen(
                    movieListScreenContent = {
                        MovieListScreen(
                            viewModel = movieListViewModel,
                            onItemClick = { id ->
                                startActivity(MovieDetailActivity.newIntent(this@MovieMainActivity, id))
                            },
                            modifier = Modifier.padding(it)
                        )
                    },

                    favouriteScreenContent = {
                        MovieFavouriteScreen(
                            viewModel = favouritesViewModel,
                            modifier = Modifier.padding(it)
                        )
                    }
                )
            }
        }
    }

    companion object {

        fun newIntent(context: Context): Intent {
            val newIntent = Intent(context, MovieMainActivity::class.java)
            return newIntent
        }
    }
}
