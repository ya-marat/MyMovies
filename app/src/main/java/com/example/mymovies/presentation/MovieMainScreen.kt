package com.example.mymovies.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mymovies.R
import com.example.mymovies.navigation.AppNavGraph
import com.example.mymovies.navigation.NavigationItem
import com.example.mymovies.presentation.favourites.FavouriteMoviesViewModel
import com.example.mymovies.presentation.movielist.MovieListScreen
import com.example.mymovies.presentation.movielist.MovieListViewModel
import com.example.mymovies.presentation.viewmodels.MainViewModel

@Composable
@Preview
fun MovieMainScreenPreview() {
    MovieMainScreen(
        movieListScreenContent = {},
        favouriteScreenContent = {}
    )
}

@Composable
fun MovieMainScreen(
    movieListScreenContent: @Composable (PaddingValues) -> Unit,
    favouriteScreenContent: @Composable (PaddingValues) -> Unit
) {
    val navHostController = rememberNavController()

    val listItems = listOf(
        NavigationItem.MovieList,
        NavigationItem.Favourite
    )

    Scaffold(
        containerColor = colorResource(R.color.app_black),
        bottomBar = {
            NavigationBar(
                modifier = Modifier.dropShadow(
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

                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                listItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = currentRoute == item.screen.route,
                        onClick = { navHostController.navigate(item.screen.route) },
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
            navHostController = navHostController,
            movieListScreenContent = {
                movieListScreenContent(paddingValues)
            },
            favouriteScreenContent = {
                favouriteScreenContent(paddingValues)
            }
        )
    }
}