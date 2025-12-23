package com.example.mymovies.data.local.database.entites

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "movie_person",
)
data class MoviePersonDBEntity (
    @PrimaryKey
    val id: Int,
    val name: String?,
    val profession: String?
)