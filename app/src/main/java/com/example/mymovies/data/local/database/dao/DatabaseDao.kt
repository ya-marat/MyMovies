package com.example.mymovies.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymovies.data.local.database.dto.MovieCastDto
import com.example.mymovies.data.local.database.entites.MovieDBEntity
import com.example.mymovies.data.local.database.entites.MoviePersonDBEntity
import com.example.mymovies.data.local.database.entites.MovieActorJoin
import com.example.mymovies.data.local.database.entites.MovieGenreDBEntity

@Dao
interface DatabaseDao {

    @Insert
    suspend fun insertMovie(movieDbEntity: MovieDBEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(actorsDBEntity: List<MoviePersonDBEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieActorJoins(joins: List<MovieActorJoin>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenres(genres: List<MovieGenreDBEntity>)

    @Query("DELETE FROM favourite_movies WHERE id = :movieId")
    suspend fun removeMovieFromFavourite(movieId: Int)

    @Query("SELECT * FROM favourite_movies")
    suspend fun getAllFavourites(): List<MovieDBEntity>

    @Query("SELECT * FROM favourite_movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieDBEntity

    @Query("""
        SELECT movie_person.*, movie_actor_join.role, movie_actor_join.`order`
        FROM movie_person
        INNER JOIN movie_actor_join ON movie_person.id = movie_actor_join.actorId
        WHERE movie_actor_join.movieId = :movieId  
    """)
    suspend fun getMovieActorCast(movieId: Int): List<MovieCastDto>

    @Query("""
        SELECT movie_genres.*
        FROM movie_genres
        WHERE movie_genres.movieId = :movieId
    """)
    suspend fun getMovieGenres(movieId: Int): List<MovieGenreDBEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_movies WHERE id =  :movieId)")
    fun observeIsFavourite(movieId: Int): LiveData<Boolean>

    @Query("SELECT * FROM favourite_movies")
    fun observeFavourites(): LiveData<List<MovieDBEntity>>
}