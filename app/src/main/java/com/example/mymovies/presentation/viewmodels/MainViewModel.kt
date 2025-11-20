package com.example.mymovies.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.domain.moviesusecases.GetMoviesUseCase
import com.example.mymovies.domain.Movie
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
): ViewModel() {

    val movieList = MutableLiveData<List<Movie>>()

    fun loadMovies(){
        viewModelScope.launch {

        }
    }
}