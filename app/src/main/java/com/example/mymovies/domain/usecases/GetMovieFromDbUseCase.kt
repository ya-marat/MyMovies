package com.example.mymovies.domain.usecases

import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import com.example.mymovies.domain.common.Result
import javax.inject.Inject

class GetMovieFromDbUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun loadMovieFromDb(movieId: Int): Result<Movie> {
        return movieRepository.getMovieFromDb(movieId)
    }
}