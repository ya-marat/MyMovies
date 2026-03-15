package com.example.mymovies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

fun NavGraphBuilder.homeScreenNavGraph(
    movieListScreenContent: @Composable () -> Unit,
    detailMovieScreenContent: @Composable (Int) -> Unit
) {
    navigation(
        startDestination = Screen.MovieList.route,
        route = Screen.Home.route
    ) {
        composable(
            route = Screen.MovieList.route
        ) {
            movieListScreenContent()
        }

        composable(
            route = Screen.MovieDetails.route,
            arguments = listOf(
                navArgument(Screen.KEY_MOVIE_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val movieId = it.arguments?.getInt(Screen.KEY_MOVIE_ID) ?: 0
            detailMovieScreenContent(movieId)
        }
    }
}