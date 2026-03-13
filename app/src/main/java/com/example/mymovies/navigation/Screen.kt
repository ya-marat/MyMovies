package com.example.mymovies.navigation

sealed class Screen (
    val route: String
){

    object MovieList: Screen(ROUTE_MOVIE_LIST)
    object Favourite: Screen(ROUTE_FAVOURITE)
    object Home: Screen(ROUTE_HOME)
    object MovieDetails: Screen(ROUTE_MOVIE_DETAILS) {

        private const val ROUTE_FOR_ARGS = "movie_details"

        fun getRouteWithArgs(movieId: Int): String {
            return "$ROUTE_FOR_ARGS/$movieId"
        }
    }

    companion object {

        const val KEY_MOVIE_ID = "movie_id"

        const val ROUTE_HOME = "home"
        const val ROUTE_MOVIE_DETAILS = "movie_details/{$KEY_MOVIE_ID}"
        const val ROUTE_MOVIE_LIST = "movie_list"
        const val ROUTE_FAVOURITE = "favourite"
    }
}