package com.example.mymovies.data.local.database.entites

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "movie_genres",
    primaryKeys = ["movieId", "genre"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDBEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class MovieGenreDBEntity (
    val movieId: Int,
    val genre: String
)