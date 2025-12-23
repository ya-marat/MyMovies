package com.example.mymovies.data.local.datasource

import androidx.lifecycle.LiveData
import com.example.mymovies.data.local.database.dao.DatabaseDao
import com.example.mymovies.data.local.database.dto.MovieCastDto
import com.example.mymovies.data.local.database.entites.MovieActorJoin
import com.example.mymovies.data.local.database.entites.MoviePersonDBEntity
import com.example.mymovies.data.local.database.entites.MovieDBEntity
import com.example.mymovies.data.local.database.entites.MovieGenreDBEntity
import javax.inject.Inject

class LocalDataSourceImp @Inject constructor(
    private val databaseDao: DatabaseDao
): LocalDataSource {
    override suspend fun saveMovie(movieDBEntity: MovieDBEntity) {
        databaseDao.insertMovie(movieDBEntity)
    }

    override suspend fun saveMoviePersons(moviePersonsDBEntity: List<MoviePersonDBEntity>) {
        databaseDao.insertActors(moviePersonsDBEntity)
    }

    override suspend fun saveMovieActorJoins(movieActorJoins: List<MovieActorJoin>) {
        databaseDao.insertMovieActorJoins(movieActorJoins)
    }

    override suspend fun saveMovieGenres(movieGenresDBEntity: List<MovieGenreDBEntity>) {
        databaseDao.insertMovieGenres(movieGenresDBEntity)
    }

    override suspend fun getMovieActors_NI(movieId: Int): MoviePersonDBEntity {
        TODO("Not yet implemented")
    }

    override suspend fun removeMovie(movieId: Int) {
        databaseDao.removeMovieFromFavourite(movieId)
    }

    override suspend fun getMovieFromDb(movieId: Int): MovieDBEntity {
        return databaseDao.getMovieById(movieId)
    }

    override suspend fun getMovieActorsCast(movieId: Int): List<MovieCastDto> {
        return databaseDao.getMovieActorCast(movieId)
    }

    override suspend fun getMovieGenres(movieId: Int): List<MovieGenreDBEntity> {
        return databaseDao.getMovieGenres(movieId)
    }

    override fun observeIsFavourite(movieId: Int): LiveData<Boolean> {
        return databaseDao.observeIsFavourite(movieId)
    }
}