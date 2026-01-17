package com.example.mymovies.domain.usecases

import androidx.lifecycle.LiveData
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import com.example.mymovies.domain.common.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavouriteMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<Result<List<Movie>>> {
        return movieRepository.observeFavourites()
    }
}