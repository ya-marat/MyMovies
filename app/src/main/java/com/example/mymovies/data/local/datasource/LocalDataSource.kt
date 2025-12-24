package com.example.mymovies.data.local.datasource

import androidx.lifecycle.LiveData
import com.example.mymovies.data.local.database.dto.MovieCastDto
import com.example.mymovies.data.local.database.entites.MovieActorJoin
import com.example.mymovies.data.local.database.entites.MoviePersonDBEntity
import com.example.mymovies.data.local.database.entites.MovieDBEntity
import com.example.mymovies.data.local.database.entites.MovieGenreDBEntity

interface LocalDataSource {

    suspend fun saveMovie(movieDBEntity: MovieDBEntity)

    suspend fun saveMoviePersons(moviePersonsDBEntity: List<MoviePersonDBEntity>)

    suspend fun saveMovieActorJoins(movieActorJoins: List<MovieActorJoin>)

    suspend fun saveMovieGenres(movieGenresDBEntity: List<MovieGenreDBEntity>)

    suspend fun removeMovie(movieId: Int)

    suspend fun getMovieFromDb(movieId: Int): MovieDBEntity

    suspend fun getMovieActors_NI(movieId: Int): MoviePersonDBEntity

    suspend fun getMovieActorsCast(movieId: Int): List<MovieCastDto>

    suspend fun getMovieGenres(movieId: Int): List<MovieGenreDBEntity>
    fun observeIsFavourite(movieId: Int): LiveData<Boolean>
    suspend fun getMoviesFromDb(): List<MovieDBEntity>
}