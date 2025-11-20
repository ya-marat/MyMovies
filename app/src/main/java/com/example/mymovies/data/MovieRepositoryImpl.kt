package com.example.mymovies.data

import android.util.Log
import com.example.mymovies.data.mapper.MovieMapper
import com.example.mymovies.data.network.ApiService
import com.example.mymovies.data.network.model.MovieDto
import com.example.mymovies.data.network.model.MoviesResponseDto
import com.example.mymovies.domain.FileReaderUseCase
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.Exception

class MovieRepositoryImpl @Inject constructor(
    private val mapper: MovieMapper,
    private val apiService: ApiService,
    private val fileReaderUseCase: FileReaderUseCase
) : MovieRepository {

    companion object {
        private const val TAG = "MovieRepositoryImpl"
    }

    override suspend fun loadMovies(page: Int): List<Movie> {

        var response: MoviesResponseDto? = null

        try {
            response = apiService.loadMovies(page)
        } catch (e: HttpException) {
            Log.d(TAG, "${e.code()}\n${e.message()}")
            return loadMoviesFromFile()
        } catch (e: UnknownHostException) {
            Log.d(TAG, "${e}")
            return loadMoviesFromFile()
        } catch (e: Exception) {
            Log.d(TAG, "${e}")
            return loadMoviesFromFile()
        }

        Log.d(TAG, "Loaded: ${response?.movies?.size}")

        response?.let {
            return it.movies.map { mapper.mapDtoToEntity(it) }
        }

        return listOf()
    }

    override suspend fun loadMovieById(movieId: Int): Movie? {

        var response: MovieDto? = null

        try {
            response = apiService.loadMovieById(movieId)
        } catch (e: Exception) {
            Log.d(TAG, "${e}")
        }

        response?.let {
            return mapper.mapDtoToEntity(it)
        }

        return null
    }

    override suspend fun loadNewMovies(page: Int): List<Movie> {
        val result = runRequest {
            apiService.loadNewMovies(page)
        } ?: return listOf()

        return result.movies.map { mapper.mapDtoToEntity(it) }
    }

    override suspend fun loadPopularMovies(page: Int): List<Movie> {
        val res = runRequest { apiService.loadPopularMovies(page) } ?: return listOf()
        return res.movies.map { mapper.mapDtoToEntity(it) }
    }

    override suspend fun loadMoviesByGenre(page: Int, genre: String): List<Movie> {
        val response = runRequest { apiService.loadMoviesByGenre(page, genre) } ?: return listOf()
        return response.movies.map { mapper.mapDtoToEntity(it) }
    }

    fun loadMoviesFromFile(): List<Movie> {
        return fileReaderUseCase.loadMoviesFromFile()
    }

    private suspend fun <T> runRequest(request: suspend () -> T): T? {
        val response: T?

        try {
            response = request.invoke()
        } catch (e: HttpException) {
            Log.d(TAG, "${e.code()}\n${e.message()}")
            return null
        } catch (e: UnknownHostException) {
            Log.d(TAG, "${e}")
            return null
        } catch (e: Exception) {
            Log.d(TAG, "${e}")
            return null
        }

        return response
    }
}