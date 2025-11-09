package com.example.mymovies.data

import android.util.Log
import com.example.mymovies.data.mapper.MovieMapper
import com.example.mymovies.data.network.ApiService
import com.example.mymovies.data.network.model.MovieResponseDto
import com.example.mymovies.domain.FileReaderUseCase
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val mapper: MovieMapper,
    private val apiService: ApiService,
    private val fileReaderUseCase: FileReaderUseCase
) : MovieRepository {

    companion object{
        private const val TAG = "MovieRepositoryImpl"
    }

    override suspend fun loadMovies(page: Int): List<Movie> {

        var response: MovieResponseDto? = null

        try {
            response = apiService.loadMovies(page)
            //response = apiService.loadMovies1()
        }catch (e: HttpException) {
            Log.d(TAG, "${e.code()}\n${e.message()}")
            return loadMoviesFromFile()
        }catch (e: UnknownHostException){
            Log.d(TAG, "${e}")
            return loadMoviesFromFile()
        }catch (e: Exception){
            Log.d(TAG, "${e}")
            return loadMoviesFromFile()
        }

        Log.d(TAG, "Loaded: ${response?.movies?.size}")

        response?.let {
            return it.movies.map { mapper.mapDtoToEntity(it) }
        }

        return listOf()
    }

    fun loadMoviesFromFile(): List<Movie> {
        return fileReaderUseCase.loadMoviesFromFile()
    }
}