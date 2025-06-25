package com.example.mymovies.data

import androidx.lifecycle.LiveData
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository

class MovieRepositoryImpl: MovieRepository {
    override fun getMovies(): LiveData<List<Movie>> {
        TODO("Get from API")
    }

    override fun addMovieToFavorite(movie: Movie) {
        TODO("Not yet implemented")
    }

}