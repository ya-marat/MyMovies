package com.example.mymovies.presentation.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Visibility
import androidx.viewbinding.ViewBinding
import com.example.mymovies.R
import com.google.android.material.appbar.MaterialToolbar

abstract class BaseAppActivity: AppCompatActivity() {

    abstract val baseBinding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(baseBinding.root)
    }

    fun setupToolbar(barTitle: String, displayBackButton: Boolean = false) {

        val baseActivityToolbar =
            baseBinding.root.findViewById<MaterialToolbar>(R.id.main_toolbar) ?: return

        baseActivityToolbar.visibility = View.VISIBLE
        setSupportActionBar(baseActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayBackButton)
        supportActionBar?.title = barTitle
        baseActivityToolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}