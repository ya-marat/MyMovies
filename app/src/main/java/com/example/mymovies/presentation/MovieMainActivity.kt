package com.example.mymovies.presentation

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.example.mymovies.App
import com.example.mymovies.di.LocalMovieDetailViewModelFactory
import com.example.mymovies.di.LocalViewModelFactory
import com.example.mymovies.navigation.Screen
import com.example.mymovies.presentation.detailmovie.MovieDetailViewModel
import com.example.mymovies.presentation.movielist.ui.theme.MyMoviesTheme
import com.example.mymovies.worker.GenreWorker
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class MovieMainActivity : ComponentActivity() {

    val component by lazy {
        (application as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var movieDetailViewModelFactory: MovieDetailViewModel.Factory

    private val deepLinkRoute = MutableStateFlow<String?>(null)



    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val workManager = WorkManager.Companion.getInstance(applicationContext)
        workManager.enqueueUniquePeriodicWork(
            GenreWorker.Companion.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            GenreWorker.Companion.makeRequest()
        )

        setContent {
            val route by deepLinkRoute.collectAsState()

            CompositionLocalProvider(
                LocalViewModelFactory provides viewModelFactory,
                LocalMovieDetailViewModelFactory provides movieDetailViewModelFactory
            ) {
                MyMoviesTheme {
                    SetStatusBarStyle()
                    MovieMainScreen(startRoute = route)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val route = intent.getStringExtra(Screen.NOTIFICATION_TO_HOME_KEY)
        deepLinkRoute.value = route
    }
}