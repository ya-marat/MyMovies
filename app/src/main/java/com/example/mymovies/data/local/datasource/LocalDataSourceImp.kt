package com.example.mymovies.data.local.datasource

import android.util.Log
import com.example.mymovies.data.local.database.dao.DatabaseDao
import com.example.mymovies.data.local.database.dto.MovieCastDto
import com.example.mymovies.data.local.database.entites.MovieActorDBEntity
import com.example.mymovies.data.local.database.entites.MovieDBEntity
import com.example.mymovies.data.local.database.entites.MovieGenreDBEntity
import com.example.mymovies.domain.MovieGenre
import com.example.mymovies.domain.MovieRepository
import javax.inject.Inject

class LocalDataSourceImp @Inject constructor(
    private val databaseDao: DatabaseDao
): LocalDataSource {
    override suspend fun saveMovie(movieDBEntity: MovieDBEntity) {
        //databaseDao.insertMovieToFavourite(movieDBEntity)
        Log.d("DataSource", "Local: SaveMovie ${movieDBEntity.movieName}")
    }

    override suspend fun saveMovieActor(movieActorDBEntity: MovieActorDBEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun saveMovieGenres(movieGenreDBEntity: MovieGenreDBEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieFromDb(movieId: Int): MovieDBEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieActors(movieId: Int): MovieActorDBEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieActorsCast(movieId: Int): MovieCastDto {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieGenres(movieGenre: MovieGenre) {
        TODO("Not yet implemented")
    }
}