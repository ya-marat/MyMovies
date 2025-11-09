package com.example.mymovies.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.domain.GetMoviesUseCase
import com.example.mymovies.domain.Movie
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    companion object{
        private const val TAG = "MovieListViewModel"
    }

    private var _movies = MutableLiveData<List<Movie>>()
    private var _isLoading = MutableLiveData<Boolean>()

    private var currentPage = 1

    val movies: LiveData<List<Movie>>
        get() = _movies

    fun loadMovies() {
        viewModelScope.launch {
            _isLoading.value = true


            val newLoadedMovies = getMoviesUseCase.getMovies(currentPage)
            val currentLoadedMovies = _movies.value?.toMutableList()

            if (currentLoadedMovies != null) {
                currentLoadedMovies += newLoadedMovies
                _movies.value = currentLoadedMovies

            } else {
                _movies.value = newLoadedMovies
            }

            _isLoading.value = false
            currentPage++
        }
    }
}