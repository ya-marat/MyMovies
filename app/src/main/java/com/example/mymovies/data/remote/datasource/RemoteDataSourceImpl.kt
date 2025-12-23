package com.example.mymovies.data.remote.datasource

import android.util.Log
import com.example.mymovies.data.remote.network.ApiService
import com.example.mymovies.data.remote.network.dto.MovieDto
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {


    override suspend fun getMovieById(movieId: Int): ApiResult<MovieDto> {
        return makeRequest(
            request = { apiService.loadMovieById(movieId) },
            dataForResult = { resp -> resp }
        )
    }

    override suspend fun getMovies(page: Int): ApiResult<List<MovieDto>> {
        return makeRequest(
            request = { apiService.loadMovies(page) },
            dataForResult = { resp -> resp.movies }
        )
    }

    override suspend fun getNewMovies(page: Int): ApiResult<List<MovieDto>> {
        return makeRequest(
            request = { apiService.loadNewMovies(page) },
            dataForResult = { resp -> resp.movies }
        )
    }

    override suspend fun getPopularMovies(page: Int): ApiResult<List<MovieDto>> {
        return makeRequest(
            request = { apiService.loadPopularMovies(page) },
            dataForResult = { resp -> resp.movies }
        )
    }

    override suspend fun getMoviesByGenre(
        page: Int,
        genre: String
    ): ApiResult<List<MovieDto>> {
        return makeRequest(
            request = { apiService.loadMoviesByGenre(page, genre) },
            dataForResult = { resp -> resp.movies }
        )
    }

    private suspend fun <T, R> makeRequest(
        request: suspend () -> T,
        dataForResult: (resp: T) -> R
    ): ApiResult<R> {
        return try {
            val response = request()
            ApiResult.Success(dataForResult(response))
        } catch (e: HttpException) {
            Log.d(TAG, "${e.code()}\n${e.message()}")
            ApiResult.HttpError(e.code(), e.message())
        } catch (e: UnknownHostException) {
            Log.d(TAG, "$e")
            ApiResult.NetworkError(e)
        } catch (e: Exception) {
            ApiResult.UnknownError(e)
        }
    }

    companion object {
        private const val TAG = "RemoteDataSourceImpl"
    }
}