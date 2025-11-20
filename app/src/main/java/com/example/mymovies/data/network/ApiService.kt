package com.example.mymovies.data.network

import com.example.mymovies.Consts
import com.example.mymovies.data.network.model.MovieDto
import com.example.mymovies.data.network.model.MovieResponseDto
import com.example.mymovies.data.network.model.MoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryName


interface ApiService {

    @GET(Consts.AppRequest.MOVIES_URL)
    @Headers(Consts.AppRequest.X_API_KEY_HEADER)
    suspend fun loadMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): MoviesResponseDto

    @GET("movie?page=1&limit=10")
    @Headers(Consts.AppRequest.X_API_KEY_HEADER)
    suspend fun loadMovies1(): MoviesResponseDto

    @GET("${Consts.AppRequest.MOVIES_URL}/{id}")
    @Headers(Consts.AppRequest.X_API_KEY_HEADER)
    suspend fun loadMovieById(@Path("id") movieId: Int): MovieDto

    @GET("${Consts.AppRequest.MOVIES_URL}?limit=11&sortField=premiere.world&sortType=1&year=2025")
    @Headers(Consts.AppRequest.X_API_KEY_HEADER)
    suspend fun loadNewMovies(
        @Query("page") page: Int
    ): MoviesResponseDto

    @GET("${Consts.AppRequest.MOVIES_URL}?limit=10&rating.kp=8-9")
    @Headers(Consts.AppRequest.X_API_KEY_HEADER)
    suspend fun loadPopularMovies(
        @Query("page") page: Int
    ): MoviesResponseDto

    @GET("${Consts.AppRequest.MOVIES_URL}?limit=10")
    @Headers(Consts.AppRequest.X_API_KEY_HEADER)
    suspend fun loadMoviesByGenre(
        @Query("page") page: Int,
        @Query("genres.name") genre: String
    ): MoviesResponseDto

    companion object {
        private const val QUERY_PARAM_PAGE = "page"
    }
}