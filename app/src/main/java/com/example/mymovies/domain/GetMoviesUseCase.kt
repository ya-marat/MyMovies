package com.example.mymovies.domain

import android.util.Log
import com.example.mymovies.Consts
import com.example.mymovies.data.MovieRepositoryImpl
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor (
    private val movieRepository: MovieRepository,
) {
    suspend fun getMovies(page: Int): List<Movie> {
        Log.d("GetMoviesUseCase", "getMovies")

        if (Consts.General.USE_LOCAL_FILE_DATA){
            return (movieRepository as MovieRepositoryImpl).loadMoviesFromFile() // TEST
        }

        val result = movieRepository.loadMovies(page)
        return result
    }
}