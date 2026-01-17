package com.example.mymovies.presentation.detailmovie

sealed class FavouriteMovieOperationUIState {

    object AddFavouriteSuccess: FavouriteMovieOperationUIState()
    object AddFavouriteError: FavouriteMovieOperationUIState()
    object RemoveFavouriteSuccess: FavouriteMovieOperationUIState()
    object RemoveFavouriteError: FavouriteMovieOperationUIState()

}