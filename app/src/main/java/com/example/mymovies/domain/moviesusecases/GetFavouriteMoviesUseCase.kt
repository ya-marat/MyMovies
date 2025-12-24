package com.example.mymovies.domain.moviesusecases

import android.util.Log
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import com.example.mymovies.domain.common.Result
import javax.inject.Inject

class GetFavouriteMoviesUseCase @Inject constructor(
    val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Result<List<Movie>> {
        return movieRepository.loadFavouriteMovies()
    }
}