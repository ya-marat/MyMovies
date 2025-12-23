package com.example.mymovies.domain.moviesusecases

import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import com.example.mymovies.domain.common.Result
import javax.inject.Inject

class RemoveMovieFromDbUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie): Result<Unit> {
        return movieRepository.removeMovieFromDb(movie)
    }
}