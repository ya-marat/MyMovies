package com.example.mymovies.presentation

import com.example.mymovies.domain.common.DomainError

sealed class MovieUiError {
    object NotFound: MovieUiError()
    object Unknown: MovieUiError()
    object NoInternet: MovieUiError()
    data class Server(val code: Int): MovieUiError()
    object LocalSaveError: MovieUiError()
}