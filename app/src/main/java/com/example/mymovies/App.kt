package com.example.mymovies

import android.app.Application
import androidx.work.Configuration
import com.example.mymovies.di.DaggerAppComponent
import com.example.mymovies.worker.GenreWorkerFactory
import javax.inject.Inject

class App: Application(), Configuration.Provider {

    private val workerFactory: GenreWorkerFactory by lazy {
        component.workerFactory()
    }

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}