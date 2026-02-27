package com.example.mymovies.presentation.common

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.mymovies.R
import com.example.mymovies.empty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppAlertDialog(
    title: String,
    text: String,
    confirmButtonText: String = stringResource(R.string.ok_button),
    confirmButton: (() -> Unit)? = null,
    dismissButtonText: String = String.empty(),
    dismissButton: (() -> Unit)? = null,
    onDismiss: () -> Unit
) {

    AlertDialog(
        modifier = Modifier
            .wrapContentSize(),
        containerColor = colorResource(R.color.app_black_secondary),
        titleContentColor = colorResource(R.color.detail_text_color),
        textContentColor = colorResource(R.color.sub_text_color),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = title
            )
        },
        text = {
            Text(
                text = text
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (confirmButton != null) {
                        confirmButton()
                    } else {
                        onDismiss()
                    }
                }) {
                Text(
                    text = confirmButtonText,
                    color = colorResource(R.color.main_color_2)
                )
            }
        },
        dismissButton = {
            if (dismissButtonText.isNotEmpty() && dismissButton != null) {
                TextButton(onClick = { onDismiss() }) {
                    Text(
                        text = dismissButtonText,
                        color = colorResource(R.color.main_color_2)
                    )
                }
            }
        }
    )
}