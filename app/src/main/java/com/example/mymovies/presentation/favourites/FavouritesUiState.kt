package com.example.mymovies.presentation.favourites

sealed class FavouritesUiState {

    object Loading: FavouritesUiState()
    data class Success(val movieList: List<FavouriteMovieUi>): FavouritesUiState()
    object Failure: FavouritesUiState()
}