package com.example.mymovies.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieGenre (
    val name: String?
): Parcelable