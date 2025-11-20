package com.example.mymovies.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.example.mymovies.databinding.ActivityMovieDetailBinding
import com.example.mymovies.domain.Movie
import com.example.mymovies.presentation.fragments.MovieDetailFragment

class MovieDetailActivity : BaseAppActivity() {

    val binding by lazy {
        ActivityMovieDetailBinding.inflate(layoutInflater)
    }

    override val baseBinding: ViewBinding
        get() = binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movie = intent.extras?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(EXTRA_MOVIE, Movie::class.java)
            } else {
                it.getParcelable(EXTRA_MOVIE) as Movie?
            }
        }

        val toolbarTitle = if (movie?.name != null) movie.name else "Uknow"
        setupToolbar(toolbarTitle, true)

        val detailFragment = MovieDetailFragment.newInstance(movie)
        supportFragmentManager.beginTransaction()
            .add(binding.movieDetailFragmentContainer.id, detailFragment).commit()

    }

    companion object {

        private const val EXTRA_MOVIE = "movie"

        fun newIntent(context: Context, movie: Movie): Intent {
            val newIntent = Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE, movie)
            }

            return newIntent
        }
    }
}