package com.example.mymovies.domain.moviesusecases

import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun getMoviesByGenre(page: Int, genre: String):List<Movie> {
        return movieRepository.loadMoviesByGenre(page, genre)
    }
}