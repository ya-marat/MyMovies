package com.example.mymovies.presentation

import android.app.Application
import com.example.mymovies.di.DaggerAppComponent

class App: Application() {

    val component by lazy {
        DaggerAppComponent.builder()
            .bindContext(this)
            .build()
    }
}