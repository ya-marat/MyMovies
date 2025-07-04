package com.example.mymovies.domain

data class Movie(
    val name: String?,
    val id: Int = UNDEFINED_ID
){
    companion object {

        const val UNDEFINED_ID = -1;
    }
}
