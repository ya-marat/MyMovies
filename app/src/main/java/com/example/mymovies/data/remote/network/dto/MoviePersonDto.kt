package com.example.mymovies.data.remote.network.dto

import com.google.gson.annotations.SerializedName

data class MoviePersonDto (

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String?,

    @SerializedName("enName")
    val enName: String?,

    @SerializedName("enProfession")
    val personProfessional: String?
)