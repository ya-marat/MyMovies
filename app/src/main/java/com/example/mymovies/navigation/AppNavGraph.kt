package com.example.mymovies.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    movieListScreenContent: @Composable () -> Unit,
    favouriteScreenContent: @Composable () -> Unit,
    detailMovieScreenContent: @Composable (Int) -> Unit,
) {


    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        homeScreenNavGraph(
            movieListScreenContent = movieListScreenContent,
            detailMovieScreenContent = detailMovieScreenContent
        )

        composable(
            route = Screen.Favourite.route
        ) {
            favouriteScreenContent()
        }
    }
}