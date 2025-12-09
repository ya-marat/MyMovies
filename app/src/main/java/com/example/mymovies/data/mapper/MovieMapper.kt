package com.example.mymovies.data.mapper

import com.example.mymovies.data.local.database.entites.MovieDBEntity
import com.example.mymovies.data.remote.network.dto.MovieDto
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieGenre
import com.example.mymovies.domain.MoviePerson
import com.example.mymovies.domain.MovieProfessionType
import com.example.mymovies.domain.MovieTrailer
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun mapDtoToEntity(dto: MovieDto): Movie {
        return Movie(id = dto.id,
            name = dto.name,
            description = dto.description,
            year = dto.year,
            poster = dto.poster?.url,
            rating = dto.rating?.kp,
            previewPoster = dto.poster?.previewUrl,
            ageRating = dto.ageRating,
            genres = dto.genres?.map { MovieGenre(it?.genreName) },
            isSeries = dto.isSeries,
            moviePersons = dto.moviePersons?.filter {
                it.personProfessional != null && MovieProfessionType.values()
                    .any { t -> t.pr == it.personProfessional }
            }?.map {
                MoviePerson(
                    it.name,
                    it.enName,
                    MovieProfessionType.professionTypeByStr(it.personProfessional)
                )
            },
            movieTrailers = dto.videos?.trailers?.map { MovieTrailer(it?.url, it?.name) })
    }

    fun mapMovieDbToMovie(movieDBEntity: MovieDBEntity): Movie {
        return Movie(
            id = movieDBEntity.id,
            name = movieDBEntity.movieName,
            description = movieDBEntity.description,
            year = movieDBEntity.year,
            poster = movieDBEntity.poster,
            previewPoster = movieDBEntity.previewPoster,
            rating = movieDBEntity.rating,
            ageRating = movieDBEntity.ageRating,
            isSeries = movieDBEntity.isSeries,
            genres = listOf(),
            moviePersons = listOf(),
            movieTrailers = listOf()
        )
    }

    fun mapMovieToMovieDb(movie: Movie, posterPath: String): MovieDBEntity {
        return MovieDBEntity(
            id = movie.id,
            movieName = movie.name,
            description = movie.description,
            year = movie.year,
            poster = posterPath,
            previewPoster = posterPath,
            rating = movie.rating,
            ageRating = movie.ageRating,
            isSeries = movie.isSeries
        )
    }

//    fun mapFavouriteMovieDbModelToMovie(movieDbModel: FavouriteMovieDbModel): Movie {
//
//    }
//
//    fun mapMovieToFavouriteMovieDbModel(movie: Movie): FavouriteMovieDbModel {
//
//    }
}