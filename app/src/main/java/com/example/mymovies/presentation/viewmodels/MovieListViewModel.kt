package com.example.mymovies.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.domain.moviesusecases.GetMoviesUseCase
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.moviesusecases.GetMoviesByGenreUseCase
import com.example.mymovies.domain.moviesusecases.GetPopularMoviesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "MovieListViewModel"
    }

    private var _movies = MutableLiveData<List<Movie>>()
    private var _popularMovies = MutableLiveData<List<Movie>>()
    private var _moviesByGenre = MutableLiveData<List<Movie>>()
    private var _isLoading = MutableLiveData<Boolean>()
    private var _firstMovieElement = MutableLiveData<Movie>()

    private var currentPage = 3

    //TODO Test
    val genre = "мелодрама"

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

    private fun loadMainPageMovies() {
        viewModelScope.launch {
            _isLoading.value = true

            val newLoadedMovies = getMoviesUseCase.getMovies(currentPage)
            val lastElementsSize = newLoadedMovies.size

            if (lastElementsSize != 0){
                _firstMovieElement.value = newLoadedMovies.first()
            }

            val listMovies = newLoadedMovies.takeLast(lastElementsSize - 1)

            if (listMovies.isNotEmpty()){
                _movies.value = listMovies
            }

            val popularMoviesResult = getPopularMoviesUseCase.loadPopularMovies(currentPage)

            if (popularMoviesResult.isNotEmpty()) {
                _popularMovies.value = popularMoviesResult
            }

            val moviesByGenre = getMoviesByGenreUseCase.getMoviesByGenre(currentPage, genre)

            if (moviesByGenre.isNotEmpty()){
                _moviesByGenre.value = moviesByGenre
            }

            _isLoading.value = false
        }
    }
}