package com.example.mymovies.data.database.entitesdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymovies.domain.MovieGenre
import com.example.mymovies.domain.MoviePerson
import com.example.mymovies.domain.MovieTrailer

@Entity(tableName = "favourite_movies")
data class FavouriteMovieDbModel(
    @PrimaryKey val id: Int,
    val movieName: String?,
    val poster: String?,
)