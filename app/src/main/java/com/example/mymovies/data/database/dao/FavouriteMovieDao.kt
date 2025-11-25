package com.example.mymovies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mymovies.data.database.entitesdb.FavouriteMovieDbModel

@Dao
interface FavouriteMovieDao {

    @Query("SELECT * FROM favourite_movies")
    fun getAllFavourites(): List<FavouriteMovieDbModel>

    @Insert
    fun insertMovieToFavourite(favouriteMovieDbModel: FavouriteMovieDbModel)

    @Query("DELETE FROM favourite_movies WHERE id = :movieId")
    fun removeMovieFromFavourite(movieId: Int)
}