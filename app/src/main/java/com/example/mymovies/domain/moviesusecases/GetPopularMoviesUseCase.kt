package com.example.mymovies.domain.moviesusecases

import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun loadPopularMovies(page: Int): List<Movie> {
        return movieRepository.loadPopularMovies(page)
    }
}