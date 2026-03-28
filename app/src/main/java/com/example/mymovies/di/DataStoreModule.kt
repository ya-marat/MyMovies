package com.example.mymovies.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.mymovies.dataStore
import dagger.Module
import dagger.Provides

@Module
class DataStoreModule {

    @AppScope
    @Provides
    fun provideDataStore(application: Application): DataStore<Preferences> {
        return application.dataStore
    }
}