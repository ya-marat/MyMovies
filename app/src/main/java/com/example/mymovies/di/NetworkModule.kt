package com.example.mymovies.di

import com.example.mymovies.data.remote.network.ApiFactory
import com.example.mymovies.data.remote.network.ApiService
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideApiService(): ApiService {
        return ApiFactory.apiService
    }
}