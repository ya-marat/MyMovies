package com.example.mymovies.data.network.model

import com.google.gson.annotations.SerializedName

data class MoviesResponseDto(
    @SerializedName("docs")
    val movies: List<MovieDto>
)