package com.example.mymovies.presentation.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.R
import com.example.mymovies.databinding.FragmentMovieDetailBinding
import com.example.mymovies.domain.Movie
import com.example.mymovies.presentation.App
import com.example.mymovies.presentation.viewmodels.MovieDetailViewModel
import com.example.mymovies.presentation.ViewModelFactory
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import javax.inject.Inject

class MovieDetailFragment : Fragment() {

    private val TAG = "MovieDetailFragment"

    private lateinit var viewModel: MovieDetailViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding: FragmentMovieDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentMovieDetailBinding is null")

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        Log.d(TAG, "onAttach")
        super.onAttach(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(EXTRA_MOVIE_DATA, Movie::class.java)
        } else {
            requireArguments().getParcelable<Movie>(EXTRA_MOVIE_DATA)
        }

        viewModel = ViewModelProvider(this, viewModelFactory)[MovieDetailViewModel::class.java]

        if (movie == null)
            return

        with(binding) {
            movie.poster?.let {
                Picasso.get().load(it).into(imgDetailMoviePoster, object : Callback {
                    override fun onSuccess() {
                        tvMovieName.text = movie.name
                        val detailMovieText = "${movie.year} | ${movie.rating}"
                        tvDetailMovie.text = detailMovieText
                        tvMovieDescription.text =
                            movie.description ?: getString(R.string.description_movie_detail_is_empty)
                    }

                    override fun onError(e: Exception?) {

                    }
                })
            }
        }
    }

    companion object {
        private const val EXTRA_MOVIE_DATA = "MOVIE_DATA"

        fun newInstance(movie: Movie?): Fragment {
            return MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_MOVIE_DATA, movie)
                }
            }
        }
    }
}