package com.example.mymovies.domain

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ImageManager @Inject constructor(
    private val application: Application
) {

    suspend fun downloadAndSaveMoviePoster(url: String, id: Int): String {

        return withContext(Dispatchers.IO) {
            try {

                val bitmap = Picasso.get().load(url).get()

                val file = File(application.filesDir, "movies_img_${id}.png")
                file.parentFile?.mkdir()

                FileOutputStream(file).use { stream ->
                    bitmap.compress(
                        Bitmap.CompressFormat.PNG,
                        90,
                        stream
                    )
                }

                Log.d("ImageManager", "Saved: ${file.absolutePath}")
                file.absolutePath
            } catch (e: Exception) {
                Log.e(TAG, "Failed download or save image.\n${e.toString()}")
                throw e
            }
        }
    }

    suspend fun removeImagePoster(path: String) {
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
    }

    companion object {
        private const val TAG = "ImageManager"
    }
}