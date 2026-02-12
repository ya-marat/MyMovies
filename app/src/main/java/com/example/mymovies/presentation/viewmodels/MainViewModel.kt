package com.example.mymovies.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mymovies.domain.usecases.GetMoviesUseCase
import com.example.mymovies.navigation.NavigationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
): ViewModel() {

    private val _selectedNavItem = MutableStateFlow<NavigationItem>(NavigationItem.MovieList)
    val selectedNavItem = _selectedNavItem.asStateFlow()

    fun selectNavItem(item: NavigationItem) {
        _selectedNavItem.value = item
    }
}