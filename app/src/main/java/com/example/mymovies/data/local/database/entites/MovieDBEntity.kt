package com.example.mymovies.data.local.database.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favourite_movies"
)
data class MovieDBEntity(
    @PrimaryKey val id: Int?,
    val movieName: String?,
    val description: String?,
    val year: Int?,
    val poster: String?,
    val previewPoster: String?,
    val rating: Double?,
    val ageRating: Int?,
    val isSeries: Boolean,
)