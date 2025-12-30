package com.example.mymovies.domain.usecases

import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import com.example.mymovies.domain.common.Result
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun getMoviesByGenre(page: Int, genre: String): Result<List<Movie>> {
        return movieRepository.loadMoviesByGenre(page, genre)
    }
}