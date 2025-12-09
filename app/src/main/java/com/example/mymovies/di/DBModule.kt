package com.example.mymovies.di

import android.app.Application
import com.example.mymovies.data.local.database.AppDatabase
import com.example.mymovies.data.local.database.dao.DatabaseDao
import dagger.Module
import dagger.Provides

@Module
class DBModule {

    @Provides
    @AppScope
    fun provideDatabaseDao(application: Application): DatabaseDao {
        return AppDatabase.getInstance(application).favouriteMoviesDao()
    }
}