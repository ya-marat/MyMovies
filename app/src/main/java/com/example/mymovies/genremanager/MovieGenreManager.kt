package com.example.mymovies.genremanager

import com.example.mymovies.data.local.genre.GenrePrefsRepository
import com.example.mymovies.data.local.genre.GenreSettings
import javax.inject.Inject

class MovieGenreManager @Inject constructor(
    private val genrePrefsRepository: GenrePrefsRepository
) {

    private val genres = listOf(
        "аниме",
        "биография",
        "боевик",
        "вестерн",
        "военный",
        "детектив",
        "детский",
        "для взрослых",
        "документальный",
        "драма",
        "игра",
        "фантастика",
        "семейный",
        "мультфильм",
        "фэнтези",
        "ужасы",
        "триллер",
    )

    suspend fun getCurrentGenre(): String {
        return genrePrefsRepository.getGenreSettings().genreName
    }

    suspend fun updateGenreIfNeeded(): String? {

        val currentPrefs = genrePrefsRepository.getGenreSettings()
        val now = System.currentTimeMillis()

        val shouldUpdate = now - currentPrefs.lastUpdate > DAY_MS

        if (!shouldUpdate) {
            return null
        }

        val randomGenre = genres.filter { it != currentPrefs.genreName }.random()

        genrePrefsRepository.saveGenre(GenreSettings(randomGenre, now))
        return randomGenre
    }

    companion object {
        //private const val DAY_MS = 24 * 60 * 60 * 1000L
        private const val DAY_MS = 1000L
    }
}