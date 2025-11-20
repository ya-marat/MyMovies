package com.example.mymovies.data.mapper

import com.example.mymovies.data.network.model.GenreDto
import com.example.mymovies.data.network.model.MovieDto
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
}