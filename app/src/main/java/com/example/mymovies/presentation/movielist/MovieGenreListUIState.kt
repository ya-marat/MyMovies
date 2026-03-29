package com.example.mymovies.presentation.movielist

import com.example.mymovies.presentation.MovieUiError

sealed class MovieGenreListUIState {

    object Initial: MovieGenreListUIState()
    object Loading : MovieGenreListUIState()
    data class Success(val genreMovies: List<MovieItemUi>, val genreName: String) : MovieGenreListUIState()
    data class Error(val error: MovieUiError) : MovieGenreListUIState()
}