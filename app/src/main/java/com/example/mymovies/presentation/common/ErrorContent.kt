package com.example.mymovies.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymovies.R

@Composable
fun ErrorContent(
    errorText: String,
    buttonText: String,
    onRefreshClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorText,
            color = colorResource(R.color.detail_text_color),
            fontSize = 20.sp
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Button(
            modifier = Modifier
                .wrapContentSize(),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = colorResource(R.color.clear),
                contentColor = colorResource(R.color.main_color_2)
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, colorResource(R.color.main_color_2)),
            onClick = { onRefreshClick() }
        ) {
            Text(
                text = buttonText
            )
        }
    }
}