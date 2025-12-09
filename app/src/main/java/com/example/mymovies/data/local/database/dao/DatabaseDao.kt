package com.example.mymovies.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymovies.data.local.database.dto.MovieCastDto
import com.example.mymovies.data.local.database.entites.MovieDBEntity
import com.example.mymovies.data.local.database.entites.MovieActorDBEntity
import com.example.mymovies.data.local.database.entites.MovieActorJoin
import com.example.mymovies.data.local.database.entites.MovieGenreDBEntity

@Dao
interface DatabaseDao {

    @Insert
    suspend fun insertMovieToFavourite(movieDbEntity: MovieDBEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(actorDBEntity: MovieActorDBEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieActorJoin(join: MovieActorJoin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenres(genres: MovieGenreDBEntity)

    @Query("DELETE FROM favourite_movies WHERE id = :movieId")
    suspend fun removeMovieFromFavourite(movieId: Int)

    @Query("SELECT * FROM favourite_movies")
    suspend fun getAllFavourites(): List<MovieDBEntity>

    @Query("SELECT * FROM favourite_movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieDBEntity

    @Query("""
        SELECT movie_actor.*, movie_actor_join.role, movie_actor_join.`order`
        FROM movie_actor
        INNER JOIN movie_actor_join ON movie_actor.id = movie_actor_join.actorId
        WHERE movie_actor_join.movieId = :movieId
    """)
    suspend fun getMovieActorCast(movieId: Int): List<MovieCastDto>

    @Query("""
        SELECT movie_genres.*
        FROM movie_genres
        INNER JOIN favourite_movies ON favourite_movies.id = movie_genres.movieId
        WHERE movie_genres.movieId = :movieId
    """)
    suspend fun getMovieGenres(movieId: Int): List<MovieGenreDBEntity>

}