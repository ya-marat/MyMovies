package com.example.mymovies.data.mapper

import com.example.mymovies.data.network.model.GenreDto
import com.example.mymovies.data.network.model.MovieDto
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieGenre
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun mapDtoToEntity(dto: MovieDto) = Movie(
        id = dto.id,
        name = dto.name,
        description = dto.description,
        year = dto.year,
        poster = dto.poster?.url,
        rating = dto.rating?.kp,
        previewPoster = dto.poster?.previewUrl,
        ageRating = dto.ageRating,
        genres = dto.genres?.map { MovieGenre(it?.genreName) },
        isSeries = dto.isSeries
    )

    private fun mapGenreDtoToGenreEntity(dto: GenreDto?) = MovieGenre (dto?.genreName)
}