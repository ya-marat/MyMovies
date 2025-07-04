package com.example.mymovies.domain

interface MovieRepository {

    suspend fun loadMovies(): List<Movie>
}