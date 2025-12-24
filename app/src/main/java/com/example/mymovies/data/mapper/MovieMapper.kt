package com.example.mymovies.data.mapper

import com.example.mymovies.data.local.database.dto.MovieCastDto
import com.example.mymovies.data.local.database.entites.MovieDBEntity
import com.example.mymovies.data.local.database.entites.MovieGenreDBEntity
import com.example.mymovies.data.local.database.entites.MoviePersonDBEntity
import com.example.mymovies.data.remote.network.dto.MovieDto
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieGenre
import com.example.mymovies.domain.MoviePerson
import com.example.mymovies.domain.MovieProfessionType
import com.example.mymovies.domain.MovieTrailer
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun mapDtoToDomain(dto: MovieDto): Movie {
        return Movie(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            year = dto.year,
            urlPoster = dto.poster?.url,
            rating = dto.rating?.kp,
            previewPoster = dto.poster?.previewUrl,
            ageRating = dto.ageRating,
            genres = dto.genres?.map { MovieGenre(it?.genreName) },
            isSeries = dto.isSeries,
            moviePersons = dto.moviePersons?.filter {
                it.personProfessional != null && MovieProfessionType.entries
                    .any { t -> t.pr == it.personProfessional }
            }?.map {
                MoviePerson(
                    id = it.id,
                    name = it.name,
                    enName = it.enName,
                    professionType = MovieProfessionType.professionTypeByStr(it.personProfessional)
                )
            },
            movieTrailers = dto.videos?.trailers?.map { MovieTrailer(it?.url, it?.name) })
    }

    fun mapMovieDbToMovie(
        movieDBEntity: MovieDBEntity,
        movieCasts: List<MovieCastDto>,
        movieDbEntities: List<MovieGenreDBEntity>
    ): Movie {
        return Movie(
            id = movieDBEntity.id,
            name = movieDBEntity.movieName,
            description = movieDBEntity.description,
            year = movieDBEntity.year,
            urlPoster = movieDBEntity.posterUrl,
            localPathPoster = movieDBEntity.posterLocalPath,
            previewPoster = movieDBEntity.previewPoster,
            rating = movieDBEntity.rating,
            ageRating = movieDBEntity.ageRating,
            isSeries = movieDBEntity.isSeries,
            genres = mapMovieGenreDbToMovieGenreDomain(movieDbEntities),
            moviePersons = mapMoviePersonCastToMoviePersonDomain(movieCasts),
            movieTrailers = listOf(),
            isFavourite = true
        )
    }

    fun mapMovieDbToMovie(movieDBEntity: MovieDBEntity): Movie {
        return Movie(
            id = movieDBEntity.id,
            name = movieDBEntity.movieName,
            description = movieDBEntity.description,
            year = movieDBEntity.year,
            urlPoster = movieDBEntity.posterUrl,
            localPathPoster = movieDBEntity.posterLocalPath,
            previewPoster = movieDBEntity.previewPoster,
            rating = movieDBEntity.rating,
            ageRating = movieDBEntity.ageRating,
            isSeries = movieDBEntity.isSeries,
            genres = null,
            moviePersons = null,
            movieTrailers = null,
            isFavourite = true
        )
    }

    fun mapMovieToMovieDb(movie: Movie, posterPath: String): MovieDBEntity {
        return MovieDBEntity(
            id = movie.id,
            movieName = movie.name,
            description = movie.description,
            year = movie.year,
            posterUrl = movie.urlPoster,
            posterLocalPath = posterPath,
            previewPoster = posterPath,
            rating = movie.rating,
            ageRating = movie.ageRating,
            isSeries = movie.isSeries
        )
    }

    fun mapMoviePersonCastToMoviePersonDomain(moviePersonCasts: List<MovieCastDto>): List<MoviePerson> {
        return mutableListOf<MoviePerson>().apply {
            moviePersonCasts.forEach { movieCast ->
                add(
                    MoviePerson(
                        id = movieCast.actor.id,
                        name = movieCast.actor.name,
                        enName = movieCast.actor.name,
                        professionType = if (!movieCast.actor.profession.isNullOrBlank())
                            MovieProfessionType.valueOf(movieCast.actor.profession)
                        else
                            MovieProfessionType.NONE
                    )
                )
            }
        }
    }

    fun mapMoviePersonsDomainToMoviePersonsDb(moviePersons: List<MoviePerson>): List<MoviePersonDBEntity> {
        return mutableListOf<MoviePersonDBEntity>().apply {
            moviePersons.forEach { moviePersonDomain ->
                add(
                    MoviePersonDBEntity(
                        id = moviePersonDomain.id,
                        name = if (!moviePersonDomain.name.isNullOrBlank()) {
                            moviePersonDomain.name
                        } else {
                            moviePersonDomain.enName
                        },
                        profession = moviePersonDomain.professionType.toString()
                    )
                )
            }
        }
    }

    fun mapMovieGenresToMovieGenresDb(
        movieId: Int,
        movieGenres: List<MovieGenre>
    ): List<MovieGenreDBEntity> {
        return mutableListOf<MovieGenreDBEntity>().apply {
            movieGenres.forEach { movieGenreDomain ->
                add(
                    MovieGenreDBEntity(
                        movieId = movieId,
                        genre = movieGenreDomain.name ?: "null"
                    )
                )
            }
        }
    }

    fun mapMovieGenreDbToMovieGenreDomain(movieGenreDBEntities: List<MovieGenreDBEntity>): List<MovieGenre> {
        return mutableListOf<MovieGenre>().apply {
            movieGenreDBEntities.forEach { movieGenreDbEntity ->
                add(
                    MovieGenre(
                        name = movieGenreDbEntity.genre
                    )
                )
            }
        }
    }
}