package com.example.mymovies.domain

import android.content.Context
import com.example.mymovies.data.mapper.MovieMapper
import com.example.mymovies.data.network.model.MoviesResponseDto
import com.google.gson.Gson
import javax.inject.Inject

class FileReaderUseCase @Inject constructor(
    private val context: Context,
    private val mapper: MovieMapper
) {
    fun loadMoviesFromFile(): List<Movie> {
        val filePath = "movies_local_file.json"
        val jsonString = context.assets.open(filePath).bufferedReader().use { it.readText() }
        val movies = Gson().fromJson(jsonString, MoviesResponseDto::class.java)
        movies?.let { return it.movies.map { mapper.mapDtoToEntity(it) } }
        return listOf()
    }
}