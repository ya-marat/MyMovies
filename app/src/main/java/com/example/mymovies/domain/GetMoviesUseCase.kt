package com.example.mymovies.domain

import androidx.lifecycle.LiveData
import com.google.gson.Gson

class GetMoviesUseCase (
    private val movieRepository: MovieRepository
) {

    fun getMovies(): LiveData<List<Movie>> {
        return movieRepository.getMovies()
    }
}