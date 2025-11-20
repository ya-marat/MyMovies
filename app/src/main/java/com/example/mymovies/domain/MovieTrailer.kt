package com.example.mymovies.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieTrailer (
    val url: String?,
    var name: String?
): Parcelable