package com.example.mymovies.presentation.favourites

sealed class FavouriteMoviesUiError {
    object NotFound: FavouriteMoviesUiError()
}