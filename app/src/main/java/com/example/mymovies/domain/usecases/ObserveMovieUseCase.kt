package com.example.mymovies.domain.usecases

import androidx.lifecycle.LiveData
import com.example.mymovies.domain.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Boolean> {
        return movieRepository.observeIsFavourite(movieId)
    }
}