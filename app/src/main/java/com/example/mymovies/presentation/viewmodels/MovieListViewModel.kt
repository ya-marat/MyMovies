package com.example.mymovies.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.Consts
import com.example.mymovies.R
import com.example.mymovies.domain.moviesusecases.GetMoviesUseCase
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.common.DomainError
import com.example.mymovies.domain.common.Result
import com.example.mymovies.domain.moviesusecases.GetMoviesByGenreUseCase
import com.example.mymovies.domain.moviesusecases.GetPopularMoviesUseCase
import com.example.mymovies.presentation.common.HomeUIState
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    private val application: Application
) : ViewModel() {

    companion object {
        private const val TAG = "MovieListViewModel"
    }

    private var _movies = MutableLiveData<List<Movie>>()
    private var _popularMovies = MutableLiveData<List<Movie>>()
    private var _moviesByGenre = MutableLiveData<List<Movie>>()
    private var _isLoading = MutableLiveData<Boolean>()
    private var _firstMovieElement = MutableLiveData<Movie>()
    private var _state = MutableLiveData<HomeUIState>()

    init {
        loadMainPageMovies()
    }

    val movies: LiveData<List<Movie>>
        get() = _movies

    val popularMovies: LiveData<List<Movie>>
        get() = _popularMovies

    val firstMovieElement: LiveData<Movie>
        get() = _firstMovieElement

    val moviesByGenre: LiveData<List<Movie>>
        get() = _moviesByGenre

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val state: LiveData<HomeUIState>
        get() = _state

    val genre = Consts.MovieParameters.GENRE
    var firstMovie: Movie? = null

    private fun loadMainPageMovies() {
        viewModelScope.launch {
            _isLoading.value = true

            _state.value = HomeUIState.Loading

            val currentPage = Consts.MovieParameters.PAGE //Todo Manager responsible

            val newLoadedMoviesResult = getMoviesUseCase.getMovies(currentPage)
            val popularMoviesResult = getPopularMoviesUseCase.loadPopularMovies(currentPage)
            val genreMoviesResult = getMoviesByGenreUseCase.getMoviesByGenre(currentPage, genre)

            val results = listOf(newLoadedMoviesResult, popularMoviesResult, genreMoviesResult)

            results.forEach {
                if (it is Result.Failure) {
                    _state.value = HomeUIState.Error(mapError(it.error))
                    return@launch
                }
            }

            var newMovies = (newLoadedMoviesResult as Result.Success).data

            newMovies.filter { movie -> movie.urlPoster != null }.filter { movie -> movie.localPathPoster != null }

            if (newMovies.isNotEmpty()) {
                firstMovie = newMovies.firstOrNull()
                newMovies = newMovies.takeLast(newMovies.size - 1)
            }

            _state.value = HomeUIState.Success(
                firstMovie = firstMovie,
                newMovies = newMovies,
                popularMovies = (popularMoviesResult as Result.Success).data,
                genreMovies = (genreMoviesResult as Result.Success).data
            )
        }
    }

    private fun mapError(error: DomainError): String {
        return when (error) {
            DomainError.NoInternet -> application.getString(R.string.no_internet_error)
            DomainError.NotFound -> application.getString(R.string.not_found_error)
            is DomainError.Server -> application.getString(R.string.server_error)
            is DomainError.Unknown -> application.getString(R.string.unknow_error)
            else -> {""}
        }
    }
}