package com.example.mymovies.data.network

import android.media.audiofx.DynamicsProcessing.Limiter
import com.example.mymovies.Consts
import com.example.mymovies.data.network.model.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiService {

    @GET(Consts.AppRequest.MOVIES_URL)
    @Headers(Consts.AppRequest.X_API_KEY_HEADER)
    suspend fun loadMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): MovieResponseDto

    @GET("movie?page=1&limit=10")
    @Headers(Consts.AppRequest.X_API_KEY_HEADER)
    suspend fun loadMovies1(): MovieResponseDto

    companion object {
        private const val QUERY_PARAM_PAGE = "page"
    }
}