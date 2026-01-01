package com.example.mymovies.presentation.detailmovie

data class MovieDetailUI(
    val id: Int,
    val name: String,
    val description: String?,
    val year: String,
    val rating: String,
    val ageRating: String,
    val posterUrl: String,
    val posterLocalPath: String,
    val isFavourite: Boolean,
    val actors: String,
    val creators: String,
    val genres: String,
    val trailers: List<MovieDetailTrailerUi>
)
