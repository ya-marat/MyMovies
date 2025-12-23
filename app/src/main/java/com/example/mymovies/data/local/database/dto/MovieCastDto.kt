package com.example.mymovies.data.local.database.dto

import androidx.room.Embedded
import com.example.mymovies.data.local.database.entites.MoviePersonDBEntity

data class MovieCastDto(
    @Embedded
    val actor: MoviePersonDBEntity,
    val role: String,
    val order: Int
)
