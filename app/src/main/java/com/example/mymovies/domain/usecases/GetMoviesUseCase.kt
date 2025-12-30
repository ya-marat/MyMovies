package com.example.mymovies.domain.usecases

import android.util.Log
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import com.example.mymovies.domain.common.Result
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor (
    private val movieRepository: MovieRepository,
) {
    suspend fun getMovies(page: Int): Result<List<Movie>> {
        Log.d("GetMoviesUseCase", "getMovies")
        val result = movieRepository.loadNewMovies(page)
        return result
    }
}