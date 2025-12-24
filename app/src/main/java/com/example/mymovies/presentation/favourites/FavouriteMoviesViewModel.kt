package com.example.mymovies.presentation.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.common.Result
import com.example.mymovies.domain.moviesusecases.GetFavouriteMoviesUseCase
import com.example.mymovies.presentation.MoviePresentationMapper
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteMoviesViewModel @Inject constructor(
    val getFavouriteMoviesUseCase: GetFavouriteMoviesUseCase,
    val moviePresentationMapper: MoviePresentationMapper
): ViewModel() {

    private val _state = MutableLiveData<FavouritesUiState>()
    val state: LiveData<FavouritesUiState>
        get() = _state

    fun loadFavourites() {
        viewModelScope.launch {
            when(val favouriteMoviesRequestResult = getFavouriteMoviesUseCase()) {
                is Result.Failure -> {
                    _state.value = FavouritesUiState.Failure
                }

                is Result.Success<List<Movie>> -> {
                    val favouriteMovies = favouriteMoviesRequestResult.data
                    val moviesForUi = favouriteMovies.map { moviePresentationMapper.mapMovieToFavouriteMovieUi(it) }
                    _state.value = FavouritesUiState.Success(moviesForUi)
                }
            }
        }
    }
}