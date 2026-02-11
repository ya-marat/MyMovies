package com.example.mymovies.presentation.favourites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mymovies.App
import com.example.mymovies.databinding.FragmentFavouritesMovieBinding
import com.example.mymovies.presentation.ViewModelFactory
import com.example.mymovies.presentation.detailmovie.MovieDetailActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieFavouriteFragment : Fragment() {

    private val TAG = "MovieFavouritesFragment"

    private var _binding: FragmentFavouritesMovieBinding? = null
    private val binding: FragmentFavouritesMovieBinding
        get() = _binding ?: throw RuntimeException("FragmentFavouritesMovieBinding is null")

    private val component by lazy {
        ((requireActivity()).application as App).component
    }

    private lateinit var viewModel: FavouriteMoviesViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, viewModelFactory)[FavouriteMoviesViewModel::class.java]

        val favouriteMoviesAdapter = MovieFavouritesAdapter(requireActivity())
        binding.rwFavouriteList.adapter = favouriteMoviesAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favouriteFlowTest.collect { state ->
                    when (state) {
                        is FavouritesUiState.Failure -> {
                            displayLoadingView(false)
                        }

                        is FavouritesUiState.Loading -> {
                            displayLoadingView(true)
                        }

                        is FavouritesUiState.Success -> {
                            displayLoadingView(false)
                            favouriteMoviesAdapter.submitList(state.movieList)
                        }
                    }
                }
            }
        }

        favouriteMoviesAdapter.onElementClick = { favouriteMovie ->
            val intent = MovieDetailActivity.newIntent(
                requireActivity(),
                favouriteMovie.title,
                favouriteMovie.id
            )
            startActivity(intent)
        }

        favouriteMoviesAdapter.onRemoveMovieClick = { movieId ->
            viewModel.removeFavourite(movieId)
        }

        //viewModel.loadFavourites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayLoadingView(isLoading: Boolean) {
        binding.pbLoadingFavouritesMovie.visibility =
            if (isLoading) View.VISIBLE else View.INVISIBLE
        binding.rwFavouriteList.visibility = if (!isLoading) View.VISIBLE else View.INVISIBLE
    }
}