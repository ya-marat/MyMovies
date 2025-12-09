package com.example.mymovies.data.remote.network.dto

import com.google.gson.annotations.SerializedName

data class MoviePersonDto (

    @SerializedName("name")
    val name: String?,

    @SerializedName("enName")
    val enName: String?,

    @SerializedName("enProfession")
    val personProfessional: String?
){
    companion object{
        const val ACTOR_PROFESSION = "actor"
        const val DIRECTOR_PROFESSION = "director"
        const val PRODUCER_PROFESSION = "producer"
    }
}