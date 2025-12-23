package com.example.mymovies.data.remote.datasource

sealed class ApiResult<out T> {
    data class Success<T>(val data: T): ApiResult<T>()
    data class NetworkError(val e: Throwable): ApiResult<Nothing>()
    data class HttpError(val code: Int, val message: String): ApiResult<Nothing>()
    data class UnknownError(val e: Throwable): ApiResult<Nothing>()
}