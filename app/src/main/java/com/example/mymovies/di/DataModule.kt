package com.example.mymovies.di

import android.app.Application
import com.example.mymovies.data.MovieRepositoryImpl
import com.example.mymovies.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @AppScope
    @Binds
    fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository
}