package com.example.mymovies.presentation.activities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.mymovies.R
import com.example.mymovies.databinding.ActivityMovieMainBinding
import com.example.mymovies.presentation.MainActivityState
import com.example.mymovies.presentation.NavigateFragment
import com.example.mymovies.presentation.favourites.MovieFavouriteFragment
import com.example.mymovies.presentation.fragments.MovieListFragment

class MovieMainActivity : BaseAppActivity(), NavigateFragment {

    private val TAG = "MovieMainActivity"

    private lateinit var currentState: MainActivityState
    private val fragmentsMap = mutableMapOf<MainActivityState, Fragment>()

    private val binding by lazy {
        ActivityMovieMainBinding.inflate(layoutInflater)
    }

    override val baseBinding: ViewBinding
        get() = binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragmentsMap()
        initBottomMenu()

        switchState(MainActivityState.HOME)
    }

    private fun initBottomMenu() {

        binding.movieMainBottomNavigation.setOnItemSelectedListener { it ->
            when (it.itemId) {
                R.id.home_item -> {
                    switchState(MainActivityState.HOME)
                    true
                }

                R.id.favourites_bottom_item -> {
                    switchState(MainActivityState.FAVOURITES)
                    true
                }

                else -> false
            }
        }
    }

    private fun initFragmentsMap() {

        with(fragmentsMap) {
            put(MainActivityState.HOME, MovieListFragment())
            put(MainActivityState.FAVOURITES, MovieFavouriteFragment())
        }
    }

    private fun switchState(state: MainActivityState) {

        currentState = state

        if (!fragmentsMap.containsKey(currentState)) {
            return
        }

        fragmentsMap[currentState]?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, it, currentState.toString())
                .addToBackStack(currentState.toString())
                .commit()
        }
    }

    override fun navigateToFragmentWithParameter(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(currentState.toString()).commit()
    }
}