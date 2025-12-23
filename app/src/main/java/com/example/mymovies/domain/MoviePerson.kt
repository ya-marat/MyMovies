package com.example.mymovies.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviePerson(
    val id: Int,
    val name: String?,
    val enName: String?,
    val professionType: MovieProfessionType?
): Parcelable