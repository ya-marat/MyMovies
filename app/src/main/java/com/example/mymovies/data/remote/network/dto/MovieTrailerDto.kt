package com.example.mymovies.data.remote.network.dto

import com.google.gson.annotations.SerializedName

data class MovieTrailerDto (

    @SerializedName("url")
    val url: String?,

    @SerializedName("name")
    val name: String?
)