package com.example.mymovies.presentation.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import com.example.mymovies.domain.moviesusecases.AddMovieToDbUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val addMovieToDbUseCase: AddMovieToDbUseCase
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

    fun addMovieToFavourites(movie: Movie) {
        viewModelScope.launch {
            addMovieToDbUseCase(movie)
        }
    }
}