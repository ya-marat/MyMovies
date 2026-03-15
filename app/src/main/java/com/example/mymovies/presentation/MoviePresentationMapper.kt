package com.example.mymovies.presentation

import android.app.Application
import com.example.mymovies.R
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieProfessionType
import com.example.mymovies.domain.common.DomainError
import com.example.mymovies.empty
import com.example.mymovies.presentation.detailmovie.MovieDetailTrailerUi
import com.example.mymovies.presentation.detailmovie.MovieDetailUI
import com.example.mymovies.presentation.favourites.FavouriteMovieUi
import com.example.mymovies.presentation.movielist.MovieItemUi
import javax.inject.Inject

class MoviePresentationMapper @Inject constructor(
    private val application: Application
) {

    fun mapMovieToFavouriteMovieUi(movie: Movie): FavouriteMovieUi {
        return FavouriteMovieUi(
            id = movie.id,
            title = movie.name ?: String.empty(),
            posterPath = movie.localPathPoster
        )
    }

    fun mapMovieToMovieDetailUI(movie: Movie): MovieDetailUI {
        return MovieDetailUI(
            id = movie.id,
            name = movie.name ?: String.empty(),
            movieDetail = "${movie.year} | ${movie.rating}",
            description = movie.description ?: application.getString(R.string.description_movie_detail_is_empty),
            year = movie.year?.toString() ?: String.empty(),
            rating = movie.rating?.toString() ?: String.empty(),
            ageRating = movie.ageRating?.toString() ?: String.empty(),
            posterUrl = movie.urlPoster ?: String.empty(),
            posterLocalPath = movie.localPathPoster ?: String.empty(),
            isFavourite = movie.isFavourite,
            actors = movie.moviePersons
                ?.filter { person -> person.professionType == MovieProfessionType.ACTOR }
                ?.joinToString(", ") { person ->
                    if (!person.name.isNullOrBlank())
                        person.name
                    else
                        person.enName.toString()
                } ?: String.empty(),
            creators = movie.moviePersons
                ?.filter { person -> person.professionType != MovieProfessionType.ACTOR }
                ?.joinToString(", ") { person ->
                    if (!person.name.isNullOrBlank())
                        person.name
                    else
                        person.enName.toString()
                } ?: String.empty(),
            genres = movie.genres?.joinToString(", ") { genre -> genre.name.toString() } ?: String.empty(),
            trailers = movie.movieTrailers?.map { trailer ->
                MovieDetailTrailerUi(
                    trailerName = trailer.name,
                    trailerUrl = trailer.url
                )
            } ?: listOf()
        )
    }

    fun mapDomainToMovieItemUi(domainModel: Movie): MovieItemUi {
        return MovieItemUi(
            id = domainModel.id,
            posterUrl = domainModel.urlPoster,
            localPosterPath = domainModel.localPathPoster
        )
    }

    fun mapDomainErrorToMovieUiError(domainError: DomainError): MovieUiError {
        return when (domainError) {
            DomainError.LocalSaveError -> MovieUiError.LocalSaveError
            DomainError.NoInternet -> MovieUiError.NoInternet
            DomainError.NotFound -> MovieUiError.NotFound
            is DomainError.Server -> MovieUiError.Server(domainError.code)
            is DomainError.Unknown -> MovieUiError.Unknown
        }
    }
}