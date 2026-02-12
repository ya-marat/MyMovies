package com.example.mymovies.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.mymovies.R

sealed class NavigationItem (
    val screen: Screen,
    val titleResId: Int,
    val icon: Int
) {

    object MovieList: NavigationItem (
        screen = Screen.MovieList,
        titleResId = R.string.home_bottom_item,
        icon = R.drawable.home_icon
    )

    object Favourite: NavigationItem (
        screen = Screen.Favourite,
        titleResId = R.string.favourites_bottom_item,
        icon = R.drawable.favourites_icon
    )
}