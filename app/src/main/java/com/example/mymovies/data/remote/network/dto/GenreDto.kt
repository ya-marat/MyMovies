package com.example.mymovies.data.remote.network.dto

import com.google.gson.annotations.SerializedName

data class GenreDto (

    @SerializedName("name")
    val genreName: String?
)