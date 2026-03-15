package com.example.mymovies.presentation.movielist

import com.example.mymovies.domain.Movie
import com.example.mymovies.presentation.MovieUiError

sealed class MovieListUiState {

    object Initial: MovieListUiState()
    object Loading: MovieListUiState()

    data class Success(
        val firstMovie: MovieItemUi?,
        val newMovies: List<MovieItemUi>,
        val popularMovies: List<MovieItemUi>,
        val genreMovies: List<MovieItemUi>,
    ): MovieListUiState()

    data class Error(val error: MovieUiError): MovieListUiState()
}