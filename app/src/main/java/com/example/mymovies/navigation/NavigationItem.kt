package com.example.mymovies.navigation
import com.example.mymovies.R

sealed class NavigationItem (
    val screen: Screen,
    val titleResId: Int,
    val icon: Int
) {

    object Home: NavigationItem (
        screen = Screen.Home,
        titleResId = R.string.home_bottom_item,
        icon = R.drawable.ic_tab_home
    )

    object Favourite: NavigationItem (
        screen = Screen.Favourite,
        titleResId = R.string.favourites_bottom_item,
        icon = R.drawable.ic_tab_favourites
    )
}