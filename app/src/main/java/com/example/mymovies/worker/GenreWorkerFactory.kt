package com.example.mymovies.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.mymovies.genremanager.MovieGenreManager
import com.example.mymovies.presentation.notification.NotificationHelper
import javax.inject.Inject

class GenreWorkerFactory @Inject constructor(
    val genreManager: MovieGenreManager,
    val notificationHelper: NotificationHelper
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return GenreWorker(
            appContext,
            workerParameters,
            genreManager,
            notificationHelper
        )
    }
}