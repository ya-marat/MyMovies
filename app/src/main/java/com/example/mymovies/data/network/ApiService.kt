package com.example.mymovies.data.network

import com.example.mymovies.Consts
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET(Consts.MOVIES_URL)
    fun loadMovies(@Query(QUERY_PARAM_PAGE) page: Int);

    companion object {
        private const val QUERY_PARAM_PAGE = "page"
    }
}