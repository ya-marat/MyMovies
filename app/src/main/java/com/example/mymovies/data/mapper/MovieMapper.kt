package com.example.mymovies.data.mapper

import com.example.mymovies.data.network.model.MovieDto
import com.example.mymovies.domain.Movie

class MovieMapper {

    fun mapDtoToEntity(dto: MovieDto) = Movie(
            name = dto.name,
            id = dto.id
        )
}