package com.example.mymovies.di

import android.app.Application
import com.example.mymovies.presentation.activities.MainActivity
import com.example.mymovies.presentation.detailmovie.MovieDetailFragment
import com.example.mymovies.presentation.favourites.MovieFavouriteFragment
import com.example.mymovies.presentation.movielist.MovieListFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        DBModule::class,
        DataSourceModule::class]
)
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(movieListFragment: MovieListFragment)
    fun inject(movieDetailFragment: MovieDetailFragment)
    fun inject(movieFavouriteFragment: MovieFavouriteFragment)

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}