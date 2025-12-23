package com.example.mymovies.presentation.common

import com.example.mymovies.domain.Movie

sealed class DetailMovieUIState {

    object Loading : DetailMovieUIState()

    data class Success(
        val movie: Movie
    ) : DetailMovieUIState()

    data class Error(val message: String) : DetailMovieUIState()
}