package com.example.mymovies.data.remote.network.dto

import com.example.mymovies.domain.MovieTrailer
import com.google.gson.annotations.SerializedName

data class MovieVideosDto (

    @SerializedName("trailers")
    val trailers: List<MovieTrailer?>
)