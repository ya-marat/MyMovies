package com.example.mymovies.domain

import android.app.Application
import com.example.mymovies.data.mapper.MovieMapper
import com.example.mymovies.data.remote.network.dto.MoviesResponseDto
import com.google.gson.Gson
import javax.inject.Inject

class FileReaderUseCase @Inject constructor(
    private val application: Application,
    private val mapper: MovieMapper
) {
    fun loadMoviesFromFile(): List<Movie> {
        val filePath = "movies_local_file.json"
        val jsonString = application.assets.open(filePath).bufferedReader().use { it.readText() }
        val movies = Gson().fromJson(jsonString, MoviesResponseDto::class.java)
        movies?.let { return it.movies.map { mapper.mapDtoToDomain(it) } }
        return listOf()
    }
}