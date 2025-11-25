package com.example.mymovies.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.util.Log
import android.widget.ImageView
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ImageManager @Inject constructor(
    private val context: Context
) {

    fun trySaveImage(imageView: ImageView, id:Int): String {
        val drawable = imageView.drawable
        if (drawable is BitmapDrawable) {
            val imageBitmap  = drawable.bitmap
            val file = File(context.filesDir, "movies_img_$id.png")
            file.parentFile?.mkdir()

            FileOutputStream(file).use { stream -> imageBitmap.compress(Bitmap.CompressFormat.PNG, 85, stream) }

            Log.d("ImageManager", "Saved: ${file.absolutePath}")
            return file.absolutePath
        }

        return ""
    }

    fun loadImage(path: String): Bitmap? {
        return BitmapFactory.decodeFile(path)
    }
}