package com.example.mymovies.domain

import androidx.lifecycle.LiveData

class GetMoviesUseCase (
    private val movieRepository: MovieRepository
) {

    suspend fun getMovies(): List<Movie> {
        return movieRepository.loadMovies()
    }
}