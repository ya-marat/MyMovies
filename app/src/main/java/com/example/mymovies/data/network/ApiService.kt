package com.example.mymovies.data.network

import com.example.mymovies.Consts
import com.example.mymovies.data.network.model.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiService {

    @GET(Consts.AppRequest.MOVIES_URL)
    @Headers(Consts.AppRequest.X_API_KEY_HEADER)
    suspend fun loadMovies(@Query("page") page: Int): MovieResponseDto

    @GET("movie?token=KMKT0ZR-ABQMBF0-P13XZ4F-XY9VJM6&&rating.kp=4-8&sortField=votes.kp&sortType=-1&limit=30")
    suspend fun loadMovies1(@Query("page") page: Int): MovieResponseDto

    companion object {
        private const val QUERY_PARAM_PAGE = "page"
    }
}