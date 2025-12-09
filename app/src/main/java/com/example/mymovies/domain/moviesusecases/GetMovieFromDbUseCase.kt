package com.example.mymovies.domain.moviesusecases

import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import javax.inject.Inject

class GetMovieFromDbUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun loadMovieFromDb(movieId: Int): Movie {
        return movieRepository.getMovieFromDb(movieId)
    }
}