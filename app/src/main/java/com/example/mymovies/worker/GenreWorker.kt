package com.example.mymovies.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.mymovies.genremanager.MovieGenreManager
import com.example.mymovies.presentation.notification.NotificationHelper
import java.util.concurrent.TimeUnit

class GenreWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val genreManager: MovieGenreManager,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        Log.d("GenreWorker", "doWork")
        val newGenre = genreManager.updateGenreIfNeeded()
        newGenre?.let { genre ->
            notificationHelper.showGenreMessage(genre)
        }

        return Result.success()
    }

    companion object {
        const val WORK_NAME = "genre_work"

        fun makeRequest(): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<GenreWorker>(1, TimeUnit.MINUTES)
                .build()
        }
    }
}