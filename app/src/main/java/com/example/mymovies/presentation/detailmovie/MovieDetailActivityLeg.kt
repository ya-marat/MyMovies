package com.example.mymovies.presentation.detailmovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.example.mymovies.databinding.ActivityMovieDetailBinding
import com.example.mymovies.domain.Movie
import com.example.mymovies.empty
import com.example.mymovies.presentation.activities.BaseAppActivity
import kotlin.jvm.java

class MovieDetailActivityLeg : BaseAppActivity() {

    val binding by lazy {
        ActivityMovieDetailBinding.inflate(layoutInflater)
    }

    override val baseBinding: ViewBinding
        get() = binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movieName = intent.getStringExtra(EXTRA_MOVIE_NAME)
        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, Movie.UNDEFINED_ID)


        val toolbarTitle = movieName ?: String.empty()
        setupToolbar(toolbarTitle, true)

        val detailFragment = MovieDetailFragmentLeg.newInstance(movieId)
        supportFragmentManager.beginTransaction()
            .add(binding.movieDetailFragmentContainer.id, detailFragment).commit()

    }

    companion object {

        private const val EXTRA_MOVIE_NAME = "movie_name"
        private const val EXTRA_MOVIE_ID = "movie_id"

        fun newIntent(context: Context, movieName: String, movieId: Int): Intent {
            val newIntent = Intent(context, MovieDetailActivityLeg::class.java).apply {
                putExtra(EXTRA_MOVIE_NAME, movieName)
                putExtra(EXTRA_MOVIE_ID, movieId)
            }

            return newIntent
        }
    }
}