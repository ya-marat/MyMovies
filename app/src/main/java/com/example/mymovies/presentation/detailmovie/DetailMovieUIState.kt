package com.example.mymovies.presentation.detailmovie

sealed class DetailMovieUIState {

    object Initial: DetailMovieUIState()
    object Loading : DetailMovieUIState()

    data class Success(
        val movie: MovieDetailUI
    ) : DetailMovieUIState()

    data class Error(val message: String) : DetailMovieUIState()
}