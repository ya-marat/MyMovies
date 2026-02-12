package com.example.mymovies.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    movieListScreenContent: @Composable () -> Unit,
    favouriteScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.MovieList.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = Screen.MovieList.route
        ) {
            movieListScreenContent()
        }

        composable(
            route = Screen.Favourite.route
        ) {
            favouriteScreenContent()
        }
    }
}