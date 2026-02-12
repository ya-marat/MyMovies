package com.example.mymovies.presentation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun SetStatusBarStyle(
    statusBarColor: Color = Color.Black,
    darkIcons: Boolean = false
) {
    val view = LocalView.current

    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = statusBarColor.toArgb()

        WindowCompat.getInsetsController(window, view)
            .isAppearanceLightStatusBars = darkIcons
    }
}