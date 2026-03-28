package com.example.mymovies

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

fun String.Companion.empty(): String {
    return ""
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("app_settings")