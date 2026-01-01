package com.example.mymovies.presentation.detailmovie

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
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
import com.example.mymovies.presentation.common.DetailMovieUIState
import com.example.mymovies.presentation.common.FavouriteMovieOperationUIState
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

    private val _isFavouriteMovieLd = MediatorLiveData<Boolean>()
    val isFavourite: LiveData<Boolean> = _isFavouriteMovieLd

    private val _favouriteMovieOperationState = MutableLiveData<FavouriteMovieOperationUIState>()
    val favouriteMovieOperationUIState: LiveData<FavouriteMovieOperationUIState> =
        _favouriteMovieOperationState

    private val _state = MutableLiveData<DetailMovieUIState>()
    val state: LiveData<DetailMovieUIState> = _state

    private lateinit var currentMovie: Movie

    fun observeMovie(movieId: Int): LiveData<Boolean> {
        return observeMovieUseCase(movieId)
    }

    fun loadMovieById(movieId: Int) {
        viewModelScope.launch {

            _state.value = DetailMovieUIState.Loading

            when (val loadedMovieResult = getMovieByIdUseCase(movieId)) {

                is Result.Failure -> {
                    _state.value = DetailMovieUIState.Error(mapError(loadedMovieResult.error))
                }

                is Result.Success<Movie> -> {
                    currentMovie = loadedMovieResult.data

                    val observeFavourite = observeMovieUseCase(movieId)

                    _isFavouriteMovieLd.addSource(observeFavourite) { isFavourite ->

                        _isFavouriteMovieLd.value = isFavourite
                        currentMovie = currentMovie.copy(isFavourite = isFavourite)
                    }

                    val movieDetailUI = moviePresentationMapper.mapMovieToMovieDetailUI(loadedMovieResult.data)

                    _state.value = DetailMovieUIState.Success(movieDetailUI)
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

            _favouriteMovieOperationState.value = favouriteOperationState
        }
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

    fun addMovieToFavourites() {
        viewModelScope.launch {
            addMovieToDbUseCase(currentMovie)
        }
    }
}