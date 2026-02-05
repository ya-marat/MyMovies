package com.example.mymovies.presentation.detailmovie

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.R
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.common.DomainError
import com.example.mymovies.domain.common.Result
import com.example.mymovies.domain.usecases.AddMovieToDbUseCase
import com.example.mymovies.domain.usecases.GetMovieByIdUseCase
import com.example.mymovies.domain.usecases.ObserveMovieUseCase
import com.example.mymovies.domain.usecases.RemoveMovieFromDbUseCase
import com.example.mymovies.presentation.MoviePresentationMapper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val addMovieToDbUseCase: AddMovieToDbUseCase,
    private val removeMovieFromDbUseCase: RemoveMovieFromDbUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val observeMovieUseCase: ObserveMovieUseCase,
    private val application: Application,
    private val moviePresentationMapper: MoviePresentationMapper
) : ViewModel() {

    private val _state = MutableStateFlow<DetailMovieUIState>(DetailMovieUIState.Initial)
    val state: StateFlow<DetailMovieUIState> = _state.asStateFlow()

    private val _isFavouriteMovieFlow = MutableStateFlow<Boolean>(false)
    val isFavouriteMovie: StateFlow<Boolean> = _isFavouriteMovieFlow.asStateFlow()

    private val _favouriteMovieOperationUIStateFlow =
        MutableSharedFlow<FavouriteMovieOperationUIState>()
    val favouriteMovieOperationUIStateFlow = _favouriteMovieOperationUIStateFlow.asSharedFlow()


    private lateinit var currentMovie: Movie

    fun loadMovieById(movieId: Int) {
        viewModelScope.launch {

            _state.value = DetailMovieUIState.Loading
            val loadedMovieResult = getMovieByIdUseCase(movieId)
            Log.d("MovieDetailViewModel", "Error: ${loadedMovieResult}")
            when (loadedMovieResult) {

                is Result.Failure -> {

                    _state.value = DetailMovieUIState.Error(mapError(loadedMovieResult.error))
                }

                is Result.Success<Movie> -> {
                    currentMovie = loadedMovieResult.data
                    Log.d("MovieDetailViewModel", "loadMovieById: $currentMovie")

                    observeMovieUseCase(movieId)
                        .onEach { isFavourite ->
                            currentMovie = currentMovie.copy(isFavourite = isFavourite)
                            _isFavouriteMovieFlow.emit(isFavourite)

                        }
                        .launchIn(viewModelScope)

                    val movieDetailUI =
                        moviePresentationMapper.mapMovieToMovieDetailUI(loadedMovieResult.data)
                    _state.emit(DetailMovieUIState.Success(movieDetailUI))
                }
            }
        }
    }

    fun onFavouriteClick() {
        viewModelScope.launch {

            val isFavourite = currentMovie.isFavourite
            val resultOperation: Result<Unit>
            var isAddOperation = false

            if (isFavourite) {
                resultOperation = removeMovieFromDbUseCase(currentMovie)
                isAddOperation = false
            } else {
                resultOperation = addMovieToDbUseCase(currentMovie)
                isAddOperation = true
            }

            val favouriteOperationState: FavouriteMovieOperationUIState

            when (resultOperation) {
                is Result.Failure -> {
                    favouriteOperationState = if (isAddOperation) {
                        FavouriteMovieOperationUIState.AddFavouriteError
                    } else {
                        FavouriteMovieOperationUIState.RemoveFavouriteError
                    }
                }

                is Result.Success<Unit> -> {
                    favouriteOperationState = if (isAddOperation) {
                        FavouriteMovieOperationUIState.AddFavouriteSuccess
                    } else {
                        FavouriteMovieOperationUIState.RemoveFavouriteSuccess
                    }
                }
            }

            _favouriteMovieOperationUIStateFlow.emit(favouriteOperationState)
        }
    }

    fun onTrailerClick() {

    }

    private fun mapError(error: DomainError): String {
        return when (error) {
            DomainError.NoInternet -> application.getString(R.string.no_internet_error)
            DomainError.NotFound -> application.getString(R.string.not_found_error)
            is DomainError.Server -> application.getString(R.string.server_error)
            is DomainError.Unknown -> application.getString(R.string.unknow_error)
            else -> {
                ""
            }
        }
    }
}