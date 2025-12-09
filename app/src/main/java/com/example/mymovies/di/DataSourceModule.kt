package com.example.mymovies.di

import com.example.mymovies.data.local.datasource.LocalDataSource
import com.example.mymovies.data.local.datasource.LocalDataSourceImp
import com.example.mymovies.data.remote.datasource.RemoteDataSource
import com.example.mymovies.data.remote.datasource.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {

    @Binds
    @AppScope
    fun bindLocalDataSource(impl: LocalDataSourceImp): LocalDataSource

    @Binds
    @AppScope
    fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource
}