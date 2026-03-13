package com.example.mymovies.presentation.detailmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MovieDetailViewModelFactory @Inject constructor(
    private val assistedFactory: MovieDetailViewModel.Factory,
    private val movieId: Int
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create(movieId) as T
    }
}