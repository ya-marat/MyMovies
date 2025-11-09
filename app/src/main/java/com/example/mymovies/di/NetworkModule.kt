package com.example.mymovies.di

import com.example.mymovies.data.network.ApiFactory
import com.example.mymovies.data.network.ApiService
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideApiService(): ApiService {
        return ApiFactory.apiService
    }
}