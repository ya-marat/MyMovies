package com.example.mymovies.domain

import androidx.lifecycle.LiveData

interface MovieRepository {

    suspend fun loadMovies(page: Int): List<Movie>
}