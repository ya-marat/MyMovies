package com.example.mymovies.data.local.genre

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mymovies.Consts
import com.example.mymovies.dataStore
import com.example.mymovies.empty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GenrePrefsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val genreFlow: Flow<GenreSettings> =
        dataStore.data.map { genre ->
            GenreSettings(
                genreName = genre[GENRE_KEY] ?: Consts.MovieParameters.GENRE,
                lastUpdate = genre[TIME_KEY] ?: System.currentTimeMillis()
            )
        }

    suspend fun saveGenre(genreSetting: GenreSettings) {
        dataStore.edit { prefs ->
            prefs[GENRE_KEY] = genreSetting.genreName
            prefs[TIME_KEY] = genreSetting.lastUpdate
        }
    }

    suspend fun getGenreSettings(): GenreSettings {
        val prefs = dataStore.data.first()
        val genreName = prefs[GENRE_KEY] ?: Consts.MovieParameters.GENRE
        val lastUpdate = prefs[TIME_KEY] ?: System.currentTimeMillis()
        return GenreSettings(genreName, lastUpdate)
    }

    companion object {
        val GENRE_KEY = stringPreferencesKey("genre_name")
        val TIME_KEY = longPreferencesKey("genre_update_time")
    }
}