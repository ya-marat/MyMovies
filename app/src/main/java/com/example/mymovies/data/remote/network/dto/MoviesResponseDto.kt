package com.example.mymovies.data.remote.network.dto

import com.google.gson.annotations.SerializedName

data class MoviesResponseDto(
    @SerializedName("docs")
    val movies: List<MovieDto>
)