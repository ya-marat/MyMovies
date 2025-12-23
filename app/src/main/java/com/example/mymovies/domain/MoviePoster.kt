package com.example.mymovies.domain

sealed class MoviePoster {
    data class UrlPoster(val url: String): MoviePoster()
    data class LocalPoster(val path: String): MoviePoster()
}