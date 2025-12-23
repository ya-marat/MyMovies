package com.example.mymovies.data.remote.datasource

import com.example.mymovies.data.remote.network.dto.MovieDto

interface RemoteDataSource {

    suspend fun getMovieById(movieId: Int): ApiResult<MovieDto>
    suspend fun getMovies(page: Int): ApiResult<List<MovieDto>>
    suspend fun getNewMovies(page: Int): ApiResult<List<MovieDto>>
    suspend fun getPopularMovies(page: Int): ApiResult<List<MovieDto>>
    suspend fun getMoviesByGenre(page: Int, genre: String): ApiResult<List<MovieDto>>
}