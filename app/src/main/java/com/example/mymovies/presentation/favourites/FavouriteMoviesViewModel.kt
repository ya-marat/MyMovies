package com.example.mymovies.presentation.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.common.Result
import com.example.mymovies.domain.usecases.ObserveFavouriteMoviesUseCase
import com.example.mymovies.domain.usecases.RemoveMovieFromDbUseCase
import com.example.mymovies.presentation.MoviePresentationMapper
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteMoviesViewModel @Inject constructor(
    val observeFavouriteMoviesUseCase: ObserveFavouriteMoviesUseCase,
    val moviePresentationMapper: MoviePresentationMapper,
    val removeMovieFromDbUseCase: RemoveMovieFromDbUseCase
) : ViewModel() {


    private var movies: List<Movie> = listOf()

    var favourites: LiveData<FavouritesUiState> = observeFavouriteMoviesUseCase()
        .map { result ->
            when (result) {
                is Result.Failure -> FavouritesUiState.Failure
                is Result.Success -> {
                    val favouriteMovies = result.data
                    movies = favouriteMovies
                    val moviesForUi =
                        favouriteMovies.map { moviePresentationMapper.mapMovieToFavouriteMovieUi(it) }
                    FavouritesUiState.Success(moviesForUi)
                }
            }
        }

    fun removeFavourite(movieId: Int) {
        viewModelScope.launch {
            val movieForRemove = movies.find { it.id == movieId }
            movieForRemove?.let {
                removeMovieFromDbUseCase(it)
            }
        }
    }
}