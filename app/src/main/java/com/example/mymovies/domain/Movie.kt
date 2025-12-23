package com.example.mymovies.domain

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Movie(
    val id: Int = UNDEFINED_ID,
    val name: String?,
    val description: String?,
    val year: Int?,
    val urlPoster: String? = null,
    val localPathPoster: String? = null,
    val previewPoster: String?,
    val rating: Double?,
    val ageRating: Int?,
    val isSeries: Boolean,
    val genres: List<MovieGenre>?,
    val moviePersons: List<MoviePerson>?,
    val movieTrailers: List<MovieTrailer>?,
    val isFavourite: Boolean = false
) : Parcelable {

    override fun toString(): String {
        return "Name: ${name}\n" +
                "Poster $urlPoster\n" +
                "Rating ${rating}\n"
    }

    companion object {

        const val UNDEFINED_ID = -1;
    }
}
