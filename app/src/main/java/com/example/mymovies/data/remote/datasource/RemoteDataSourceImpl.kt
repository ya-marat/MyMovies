package com.example.mymovies.data.remote.datasource

import android.util.Log
import com.example.mymovies.data.remote.network.ApiService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): RemoteDataSource {
    override suspend fun getMovieById(movieId: Int) {
        Log.d("DataSource", "Remote: getMovieById $movieId")
    }
}