package com.example.mymovies.presentation.movielist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.App
import com.example.mymovies.presentation.ViewModelFactory
import com.example.mymovies.presentation.movielist.ui.theme.MyMoviesTheme
import javax.inject.Inject

class MovieListActivity : ComponentActivity() {

    val component by lazy {
        (application as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel = ViewModelProvider(this, viewModelFactory)[MovieListViewModel::class.java]

        setContent {
            MyMoviesTheme {
                MovieListScreen(
                    viewModel = viewModel,
                    onItemClick = { Log.d("MovieListActivity", "OnItemClick $it") }
                )
            }
        }
    }

    companion object {

        fun newIntent(context: Context): Intent {
            val newIntent = Intent(context, MovieListActivity::class.java)
            return newIntent
        }
    }
}
