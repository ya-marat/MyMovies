package com.example.mymovies.presentation.common

import com.example.mymovies.domain.Movie
import com.example.mymovies.presentation.detailmovie.MovieDetailUI

sealed class DetailMovieUIState {

    object Loading : DetailMovieUIState()

    data class Success(
        val movie: MovieDetailUI
    ) : DetailMovieUIState()

    data class Error(val message: String) : DetailMovieUIState()
}