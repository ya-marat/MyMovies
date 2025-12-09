package com.example.mymovies.data.local.database.entites

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "movie_actor",
)
data class MovieActorDBEntity (
    @PrimaryKey
    val id: Int?,
    val name: String?
)