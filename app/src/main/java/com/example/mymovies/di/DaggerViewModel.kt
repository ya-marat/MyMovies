package com.example.mymovies.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified VM : ViewModel> daggerViewModel(
    factory: ViewModelProvider.Factory
):VM {
    return viewModel(factory = factory)
}