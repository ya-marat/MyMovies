package com.example.mymovies.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovies.data.MovieRepositoryImpl
import com.example.mymovies.domain.Movie

class MainViewModel: ViewModel() {

    val movieRepository = MovieRepositoryImpl()

    val movieList = MutableLiveData<List<Movie>>()

    fun changeMovie(){
        movieRepository.getMovies()
    }

}