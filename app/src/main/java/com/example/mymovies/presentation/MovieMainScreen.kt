package com.example.mymovies.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mymovies.R
import com.example.mymovies.navigation.AppNavGraph
import com.example.mymovies.navigation.NavigationItem
import com.example.mymovies.navigation.rememberNavigationState
import com.example.mymovies.presentation.detailmovie.DetailMovieScreen
import com.example.mymovies.presentation.favourites.MovieFavouriteScreen
import com.example.mymovies.presentation.movielist.MovieListScreen


@Composable
fun MovieMainScreen(
    startRoute: String? = null
) {


    val navigationState = rememberNavigationState()

    LaunchedEffect(startRoute) {
        startRoute?.let {
            navigationState.navHostController.navigate(it) {
                launchSingleTop = true
            }
        }
    }

    val listItems = listOf(
        NavigationItem.Home,
        NavigationItem.Favourite
    )

    Scaffold(
        containerColor = colorResource(R.color.app_black),
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .dropShadow(
                        shape = RectangleShape,
                        shadow = androidx.compose.ui.graphics.shadow.Shadow(
                            radius = 6.dp,
                            spread = 0.dp,
                            offset = DpOffset(x = 0.dp, 2.dp),
                            brush = Brush.verticalGradient(
                                listOf(
                                    colorResource(R.color.white).copy(alpha = 0.2f),
                                    Color.Transparent
                                )
                            ),
                        )
                    ),
                containerColor = colorResource(R.color.app_black),
                contentColor = colorResource(R.color.white),
                tonalElevation = 12.dp
            ) {

                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                listItems.forEachIndexed { index, item ->

                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
                        icon = {
                            Icon(
                                modifier = Modifier
                                    .size(24.dp),
                                painter = painterResource(item.icon),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = item.titleResId)
                            )
                        },
                        colors = NavigationBarItemDefaults.colors().copy(
                            selectedTextColor = colorResource(R.color.main_color_2),
                            selectedIconColor = colorResource(R.color.main_color_2),
                            unselectedTextColor = colorResource(R.color.nav_bar_default),
                            unselectedIconColor = colorResource(R.color.nav_bar_default),
                            selectedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { paddingValues ->


        AppNavGraph(
            navHostController = navigationState.navHostController,
            movieListScreenContent = {
                MovieListScreen(
                    onItemClick = { movieId ->
                        navigationState.navigateToDetail(movieId)
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            },
            favouriteScreenContent = {
                MovieFavouriteScreen(
                    onItemClick = { movieId ->
                        navigationState.navigateToDetail(movieId)
                    },
                    modifier = Modifier.padding(paddingValues),
                )
            },
            detailMovieScreenContent = { movieId ->
                DetailMovieScreen(
                    movieId = movieId,
                    onBackClick = {
                        navigationState.navHostController.popBackStack()
                    }
                )
            }
        )
    }
}