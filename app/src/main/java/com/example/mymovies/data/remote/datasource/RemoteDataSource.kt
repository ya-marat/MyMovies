package com.example.mymovies.data.remote.datasource

interface RemoteDataSource {

    suspend fun getMovieById(movieId: Int)
}