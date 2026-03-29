package com.example.mymovies.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.mymovies.R

@Composable
fun ProgressBarIndicator(
    modifier: Modifier = Modifier,
    progressIndicatorSize: Int = 64,
    strokeWidth: Int = 7
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(progressIndicatorSize.dp),
            color = colorResource(R.color.main_color_2),
            strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap,
            strokeWidth = strokeWidth.dp
        )
    }
}