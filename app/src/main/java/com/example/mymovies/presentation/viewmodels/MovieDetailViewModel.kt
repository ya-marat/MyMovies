package com.example.mymovies.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {


    private val _movie = MutableLiveData<Movie?>()
    val movie: LiveData<Movie?>
        get() = _movie

    fun loadMovieById(movieId: Int) {
        viewModelScope.launch {
            val loadedMovie = movieRepository.loadMovieById(movieId)
            _movie.value = loadedMovie
        }
    }
}