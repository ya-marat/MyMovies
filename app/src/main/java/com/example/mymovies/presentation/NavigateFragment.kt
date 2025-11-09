package com.example.mymovies.presentation

import android.os.Parcelable
import androidx.fragment.app.Fragment

interface NavigateFragment {
    fun navigateToFragmentWithParameter(fragment: Fragment)
}