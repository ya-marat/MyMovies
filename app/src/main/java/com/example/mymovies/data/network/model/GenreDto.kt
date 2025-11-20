package com.example.mymovies.data.network.model

import com.google.gson.annotations.SerializedName

data class GenreDto (

    @SerializedName("name")
    val genreName: String?
)