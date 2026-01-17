package com.example.mymovies.domain

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.example.mymovies.domain.common.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun loadMovies(page: Int): Result<List<Movie>>
    suspend fun loadMovieById(movieId: Int): Result<Movie>
    suspend fun loadNewMovies(page: Int): Result<List<Movie>>
    suspend fun loadPopularMovies(page: Int): Result<List<Movie>>
    suspend fun loadMoviesByGenre(page:Int, genre: String): Result<List<Movie>>
    suspend fun insertMovieToDb(movie: Movie): Result<Unit>
    suspend fun getMovieFromDb(movieId: Int): Result<Movie>
    fun observeIsFavourite(movieId: Int): Flow<Boolean>
    fun observeFavourites(): Flow<Result<List<Movie>>>
    suspend fun removeMovieFromDb(movie: Movie): Result<Unit>
    suspend fun loadFavouriteMovies(): Result<List<Movie>>
}