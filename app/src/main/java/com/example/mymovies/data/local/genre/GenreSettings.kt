package com.example.mymovies.data.local.genre

import androidx.datastore.preferences.core.stringPreferencesKey

data class GenreSettings(
    val genreName: String,
    val lastUpdate: Long
)
