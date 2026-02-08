package com.example.mymovies.presentation.detailmovie

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mymovies.App
import com.example.mymovies.R
import com.example.mymovies.databinding.FragmentMovieDetailBinding
import com.example.mymovies.domain.ImageManager
import com.example.mymovies.empty
import com.example.mymovies.presentation.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class MovieDetailFragmentLeg : Fragment() {

    private val TAG = "MovieDetailFragment"

    private lateinit var viewModel: MovieDetailViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageManager: ImageManager

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding: FragmentMovieDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentMovieDetailBinding is null")

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        //Log.d(TAG, "onAttach")
        super.onAttach(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        //Log.d(TAG, "onDestroy")
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

        val movieId = requireArguments().getInt(EXTRA_MOVIE_DETAIL_ID)

        viewModel = ViewModelProvider(this, viewModelFactory)[MovieDetailViewModel::class.java]

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { uiState ->
                    when (uiState) {
                        DetailMovieUIState.Initial -> {}
                        DetailMovieUIState.Loading -> {
                            switchLoadingViewState(true)
                        }

                        is DetailMovieUIState.Error -> {

                        }

                        is DetailMovieUIState.Success -> {
                            switchLoadingViewState(false)
                            bindMovieToView(uiState.movie)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
//            viewModel.isFavouriteMovie.collect { isFavourite ->
//                val color = if (isFavourite) R.color.main_color_2 else R.color.white
//                binding.ibFavourites.setColorFilter(requireActivity().getColor(color))
//            }
        }

        lifecycleScope.launch {
            viewModel.favouriteMovieOperationUIStateFlow.collect { favouriteOperationState ->
                val message = when (favouriteOperationState) {
                    FavouriteMovieOperationUIState.AddFavouriteError -> getString(R.string.error_add_favourite_toast)
                    FavouriteMovieOperationUIState.AddFavouriteSuccess -> getString(R.string.add_to_favourites_toast)
                    FavouriteMovieOperationUIState.RemoveFavouriteError -> getString(R.string.error_remove_favourite_toast)
                    FavouriteMovieOperationUIState.RemoveFavouriteSuccess -> getString(R.string.remove_from_favorites_toast)
                    FavouriteMovieOperationUIState.Initial -> { String.empty()  }
                }

                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loadMovieById(movieId)

        binding.ibFavourites.setOnClickListener {
            viewModel.onFavouriteClick()
        }
    }

    private fun switchLoadingViewState(isLoading: Boolean) {
        binding.pbLoadingMovie.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        binding.clRoot.visibility = if (!isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun bindMovieToView(movieDetail: MovieDetailUI?) {
        if (movieDetail == null) {
            return
        }

        with(binding) {

            binding.pbLoadingMovie.visibility = View.GONE
            binding.clRoot.visibility = View.VISIBLE

            Log.d(TAG, "IsFavourite ${movieDetail.isFavourite}")

            if (movieDetail.isFavourite) {
                if (movieDetail.posterLocalPath.isNotBlank()) {
                    Picasso.get().load(File(movieDetail.posterLocalPath)).into(imgDetailMoviePoster)
                }

            } else {
                if (movieDetail.posterUrl.isNotBlank()) {
                    Log.d(TAG, movieDetail.posterUrl)
                    Picasso.get().load(movieDetail.posterUrl).into(imgDetailMoviePoster)
                }
            }

            tvMovieName.text = movieDetail.name
            tvDetailMovie.text = "${movieDetail.year} | ${movieDetail.rating}"
            tvMovieDescription.text = movieDetail.description
            tvStarringValue.text = movieDetail.actors
            tvCreatorsValue.text = movieDetail.creators
            tvGenreValue.text = movieDetail.genres

            prepareTrailer(movieDetail.trailers)
        }
    }

    private fun prepareTrailer(movieTrailers: List<MovieDetailTrailerUi>?) {
        if (movieTrailers.isNullOrEmpty()) {
            binding.llTrailerSectionGroup.visibility = View.GONE
            return
        }

        val trailer = movieTrailers.first()
        binding.llTrailerSectionGroup.visibility = View.VISIBLE
        binding.tvTrailerName.text = trailer.trailerName
        binding.trailerView.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(trailer.trailerUrl)
            }
            startActivity(intent)
        }

    }

    companion object {
        private const val EXTRA_MOVIE_DETAIL_ID = "extra_movie_detail_id"

        fun newInstance(movieId: Int): Fragment {
            return MovieDetailFragmentLeg().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_MOVIE_DETAIL_ID, movieId)
                }
            }
        }
    }
}