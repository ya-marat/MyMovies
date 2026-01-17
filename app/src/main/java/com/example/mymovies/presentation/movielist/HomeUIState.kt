package com.example.mymovies.presentation.movielist

import com.example.mymovies.domain.Movie

sealed class HomeUIState {

    object Initial: HomeUIState()
    object Loading: HomeUIState()

    data class Success(
        val firstMovie: Movie?,
        val newMovies: List<Movie>,
        val popularMovies: List<Movie>,
        val genreMovies: List<Movie>,
    ): HomeUIState()

    data class Error(val message: String): HomeUIState()
}

sealed class HomeUIError{

}