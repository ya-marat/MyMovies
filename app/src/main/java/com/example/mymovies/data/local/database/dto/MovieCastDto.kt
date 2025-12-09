package com.example.mymovies.data.local.database.dto

import androidx.room.Embedded
import com.example.mymovies.data.local.database.entites.MovieActorDBEntity

data class MovieCastDto(
    @Embedded
    val actor: MovieActorDBEntity,
    val role: String,
    val order: Int
)
