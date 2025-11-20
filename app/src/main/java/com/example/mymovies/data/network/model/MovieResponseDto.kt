package com.example.mymovies.data.network.model

import com.google.gson.annotations.SerializedName

data class MovieResponseDto(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?
)