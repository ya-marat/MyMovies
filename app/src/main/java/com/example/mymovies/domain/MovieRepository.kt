package com.example.mymovies.domain

import android.graphics.Bitmap

interface MovieRepository {

    suspend fun loadMovies(page: Int): List<Movie>
    suspend fun loadMovieById(movieId: Int): Movie?
    suspend fun loadNewMovies(page: Int): List<Movie>
    suspend fun loadPopularMovies(page: Int): List<Movie>
    suspend fun loadMoviesByGenre(page:Int, genre: String): List<Movie>
    suspend fun insertMovieToDb(movie: Movie)
    suspend fun getMovieFromDb(movieId: Int): Movie
}