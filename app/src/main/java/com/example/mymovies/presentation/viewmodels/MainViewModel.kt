package com.example.mymovies.presentation.viewmodels

import android.service.autofill.Transformation
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.data.MovieRepositoryImpl
import com.example.mymovies.di.DaggerAppComponent
import com.example.mymovies.domain.GetMoviesUseCase
import com.example.mymovies.domain.Movie
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
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