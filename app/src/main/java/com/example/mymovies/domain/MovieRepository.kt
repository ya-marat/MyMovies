package com.example.mymovies.domain

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.example.mymovies.domain.common.Result

interface MovieRepository {

    suspend fun loadMovies(page: Int): Result<List<Movie>>
    suspend fun loadMovieById(movieId: Int): Result<Movie>
    suspend fun loadNewMovies(page: Int): Result<List<Movie>>
    suspend fun loadPopularMovies(page: Int): Result<List<Movie>>
    suspend fun loadMoviesByGenre(page:Int, genre: String): Result<List<Movie>>
    suspend fun insertMovieToDb(movie: Movie): Result<Unit>
    suspend fun getMovieFromDb(movieId: Int): Result<Movie>
    fun observeIsFavourite(movieId: Int): LiveData<Boolean>
    fun observeFavourites(): LiveData<Result<List<Movie>>>
    suspend fun removeMovieFromDb(movie: Movie): Result<Unit>
    suspend fun loadFavouriteMovies(): Result<List<Movie>>
}