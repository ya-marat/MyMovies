package com.example.mymovies.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.data.MovieRepositoryImpl
import com.example.mymovies.domain.GetMoviesUseCase
import com.example.mymovies.domain.Movie
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class MainViewModel: ViewModel() {

    val movieRepository = MovieRepositoryImpl()
    val movieList = MutableLiveData<List<Movie>>()
    val getMoviesUseCase = GetMoviesUseCase(movieRepository)

    fun loadMovies(){
        viewModelScope.launch {
            val movies = getMoviesUseCase.getMovies()
            movieList.value = movies
        }
    }
}