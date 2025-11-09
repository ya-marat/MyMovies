package com.example.mymovies.di

import android.content.Context
import com.example.mymovies.presentation.activities.MainActivity
import com.example.mymovies.presentation.fragments.MovieDetailFragment
import com.example.mymovies.presentation.fragments.MovieListFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, ViewModelModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(movieListFragment: MovieListFragment)
    fun inject(movieDetailFragment: MovieDetailFragment)

    @Component.Builder
    interface ComponentBuilder {

        @BindsInstance
        fun bindContext(context: Context): ComponentBuilder
        fun build(): AppComponent
    }
}