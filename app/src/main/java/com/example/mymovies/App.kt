package com.example.mymovies

import android.app.Application
import com.example.mymovies.di.DaggerAppComponent

class App: Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}