package com.example.mymovies.presentation

import com.example.mymovies.domain.Movie
import com.example.mymovies.presentation.favourites.FavouriteMovieUi
import javax.inject.Inject

class MoviePresentationMapper @Inject constructor() {

    fun mapMovieToFavouriteMovieUi(movie: Movie):  FavouriteMovieUi {
        return FavouriteMovieUi(
            id = movie.id,
            title = movie.name,
            posterPath = movie.localPathPoster
        )
    }
}