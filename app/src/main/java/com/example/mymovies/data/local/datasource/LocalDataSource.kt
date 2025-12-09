package com.example.mymovies.data.local.datasource

import com.example.mymovies.data.local.database.dto.MovieCastDto
import com.example.mymovies.data.local.database.entites.MovieActorDBEntity
import com.example.mymovies.data.local.database.entites.MovieDBEntity
import com.example.mymovies.data.local.database.entites.MovieGenreDBEntity
import com.example.mymovies.domain.MovieGenre

interface LocalDataSource {

    suspend fun saveMovie(movieDBEntity: MovieDBEntity)

    suspend fun saveMovieActor(movieActorDBEntity: MovieActorDBEntity)

    suspend fun saveMovieGenres(movieGenreDBEntity: MovieGenreDBEntity)

    suspend fun getMovieFromDb(movieId: Int): MovieDBEntity

    suspend fun getMovieActors(movieId: Int): MovieActorDBEntity

    suspend fun getMovieActorsCast(movieId: Int): MovieCastDto

    suspend fun getMovieGenres(movieGenre: MovieGenre)

}