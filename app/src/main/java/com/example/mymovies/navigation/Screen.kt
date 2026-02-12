package com.example.mymovies.navigation

sealed class Screen (
    val route: String
){

    object MovieList: Screen(ROUTE_MOVIE_LIST)
    object Favourite: Screen(ROUTE_FAVOURITE)

    private companion object {
        const val ROUTE_MOVIE_LIST = "movie_list"
        const val ROUTE_FAVOURITE = "favourite"
    }
}