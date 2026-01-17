package com.example.mymovies.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.common.Result
import com.example.mymovies.domain.usecases.ObserveFavouriteMoviesUseCase
import com.example.mymovies.domain.usecases.RemoveMovieFromDbUseCase
import com.example.mymovies.presentation.MoviePresentationMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteMoviesViewModel @Inject constructor(
    val observeFavouriteMoviesUseCase: ObserveFavouriteMoviesUseCase,
    val moviePresentationMapper: MoviePresentationMapper,
    val removeMovieFromDbUseCase: RemoveMovieFromDbUseCase
) : ViewModel() {


    private var movies: List<Movie> = listOf()

    private val _favouritesFlowState =
        MutableStateFlow<FavouritesUiState>(FavouritesUiState.Loading)
    val favouritesFlowState: StateFlow<FavouritesUiState> = _favouritesFlowState.asStateFlow()

    init {
        //observeFavourites()
    }

    val favouriteFlowTest: StateFlow<FavouritesUiState> = observeFavouriteMoviesUseCase()
        .map { result ->
            when (result) {
                is Result.Failure -> {
                    FavouritesUiState.Failure
                }

                is Result.Success<List<Movie>> -> {
                    movies = result.data.toList()
                    val mappedValues = result.data.map {
                        moviePresentationMapper.mapMovieToFavouriteMovieUi(it)
                    }
                    FavouritesUiState.Success(movieList = mappedValues)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavouritesUiState.Loading
        )

    fun observeFavourites() {

        viewModelScope.launch {
            observeFavouriteMoviesUseCase()
                .collect { result ->
                    when (result) {
                        is Result.Failure -> {
                            _favouritesFlowState.value = FavouritesUiState.Failure
                        }

                        is Result.Success<List<Movie>> -> {
                            movies = result.data.toList()
                            val mappedValues = result.data.map {
                                moviePresentationMapper.mapMovieToFavouriteMovieUi(it)
                            }
                            val state = FavouritesUiState.Success(movieList = mappedValues)
                            _favouritesFlowState.value = state
                        }
                    }
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