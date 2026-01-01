package com.example.mymovies.di

import androidx.lifecycle.ViewModel
import com.example.mymovies.presentation.favourites.FavouriteMoviesViewModel
import com.example.mymovies.presentation.viewmodels.MainViewModel
import com.example.mymovies.presentation.detailmovie.MovieDetailViewModel
import com.example.mymovies.presentation.viewmodels.MovieListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    fun bindMovieListViewModel(viewModel: MovieListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    fun bindMovieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteMoviesViewModel::class)
    fun bindFavouriteMovieViewModel(viewModel: FavouriteMoviesViewModel): ViewModel
}