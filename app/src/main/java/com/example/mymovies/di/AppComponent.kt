package com.example.mymovies.di

import android.app.Application
import com.example.mymovies.presentation.activities.MainActivity
import com.example.mymovies.presentation.detailmovie.MovieDetailActivity
import com.example.mymovies.presentation.favourites.MovieFavouriteFragment
import com.example.mymovies.presentation.movielist.MovieMainActivity
import com.example.mymovies.presentation.movielist.MovieListFragmentLeg
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
    fun inject(movieListFragmentLeg: MovieListFragmentLeg)

    @Deprecated("In future will be use a jetpack")
    fun inject(movieFavouriteFragment: MovieFavouriteFragment)

    fun inject(movieDetailActivity: MovieDetailActivity)
    fun inject(movieMainActivity: MovieMainActivity)

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}