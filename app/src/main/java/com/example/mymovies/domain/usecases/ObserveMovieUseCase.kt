package com.example.mymovies.domain.usecases

import androidx.lifecycle.LiveData
import com.example.mymovies.domain.MovieRepository
import javax.inject.Inject

class ObserveMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieId: Int): LiveData<Boolean> {
        return movieRepository.observeIsFavourite(movieId)
    }
}