package com.example.mymovies.domain

import androidx.lifecycle.LiveData

interface MovieRepository {

    fun getMovies(): LiveData<List<Movie>>
    fun addMovieToFavorite(movie: Movie)
}