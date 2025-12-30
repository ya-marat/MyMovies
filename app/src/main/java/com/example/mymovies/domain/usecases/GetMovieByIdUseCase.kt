package com.example.mymovies.domain.usecases

import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import com.example.mymovies.domain.common.Result
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id: Int): Result<Movie> {
        return movieRepository.loadMovieById(id)
    }
}