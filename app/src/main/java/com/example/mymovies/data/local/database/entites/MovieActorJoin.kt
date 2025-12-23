package com.example.mymovies.data.local.database.entites

import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
    tableName = "movie_actor_join",
    primaryKeys = ["movieId", "actorId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDBEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MoviePersonDBEntity::class,
            parentColumns = ["id"],
            childColumns = ["actorId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieActorJoin(
    val movieId: Int,
    val actorId: Int,
    val role: String,
    val order: Int
)