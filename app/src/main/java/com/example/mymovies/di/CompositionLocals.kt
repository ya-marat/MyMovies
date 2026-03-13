package com.example.mymovies.di

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.presentation.detailmovie.MovieDetailViewModel

val LocalViewModelFactory = staticCompositionLocalOf<ViewModelProvider.Factory> {
    error("ViewModelFactory not provided")
}

val LocalMovieDetailViewModelFactory = staticCompositionLocalOf<MovieDetailViewModel.Factory> {
    error("ViewModelFactory not provided")
}