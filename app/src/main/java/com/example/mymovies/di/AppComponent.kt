package com.example.mymovies.di

import android.app.Application
import com.example.mymovies.presentation.activities.MainActivity
import com.example.mymovies.presentation.detailmovie.MovieDetailActivity
import com.example.mymovies.presentation.detailmovie.MovieDetailFragmentLeg
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

    @Deprecated("In future will be use a jetpack")
    fun inject(movieDetailFragment: MovieDetailFragmentLeg)

    @Deprecated("In future will be use a jetpack")
    fun inject(movieFavouriteFragment: MovieFavouriteFragment)

    fun inject(movieDetailActivity: MovieDetailActivity)

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}