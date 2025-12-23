package com.example.mymovies.domain.common

sealed class DomainError {

    //Common Errors

    object NotFound: DomainError()
    data class Unknown(val throwable: Throwable): DomainError()

    //Remote Errors

    object NoInternet: DomainError()
    data class Server(val code: Int, val message: String): DomainError()

    //Local errors
    object LocalSaveError: DomainError()

}