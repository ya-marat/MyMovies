package com.example.mymovies.data.remote.network.dto

import com.google.gson.annotations.SerializedName

data class RatingDto(

    @SerializedName("kp")
    val kp: Double?
)