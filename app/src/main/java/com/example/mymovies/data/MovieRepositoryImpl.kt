package com.example.mymovies.data

import android.util.Log
import com.example.mymovies.Consts
import com.example.mymovies.data.mapper.MovieMapper
import com.example.mymovies.data.network.ApiFactory
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import retrofit2.HttpException

class MovieRepositoryImpl : MovieRepository {

    val apiService = ApiFactory.apiService
    val mapper = MovieMapper()

    override suspend fun loadMovies(): List<Movie> {
        val response = apiService.loadMovies(1)
        return response.movies.map { mapper.mapDtoToEntity(it) }
    }
}