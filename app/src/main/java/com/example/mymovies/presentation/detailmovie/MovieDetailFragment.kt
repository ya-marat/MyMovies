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
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.App
import com.example.mymovies.R
import com.example.mymovies.databinding.FragmentMovieDetailBinding
import com.example.mymovies.domain.ImageManager
import com.example.mymovies.domain.MoviePerson
import com.example.mymovies.domain.MovieProfessionType
import com.example.mymovies.domain.MovieTrailer
import com.example.mymovies.presentation.ViewModelFactory
import com.example.mymovies.presentation.common.DetailMovieUIState
import com.example.mymovies.presentation.common.FavouriteMovieOperationUIState
import com.squareup.picasso.Picasso
import java.io.File
import javax.inject.Inject

class MovieDetailFragment : Fragment() {

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

        viewModel.state.observe(viewLifecycleOwner) { uiState ->

            when (uiState) {
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

        viewModel.isFavourite.observe(viewLifecycleOwner) {
            val color = if (it) R.color.main_color_2 else R.color.white
            binding.ibFavourites.setColorFilter(requireActivity().getColor(color))
        }

        viewModel.favouriteMovieOperationUIState.observe(viewLifecycleOwner) { favouriteOperationState ->
            val message = when (favouriteOperationState) {
                FavouriteMovieOperationUIState.AddFavouriteError -> getString(R.string.error_add_favourite_toast)
                FavouriteMovieOperationUIState.AddFavouriteSuccess -> getString(R.string.add_to_favourites_toast)
                FavouriteMovieOperationUIState.RemoveFavouriteError -> getString(R.string.error_remove_favourite_toast)
                FavouriteMovieOperationUIState.RemoveFavouriteSuccess -> getString(R.string.remove_from_favorites_toast)
            }

            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
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
                movieDetail.posterLocalPath?.let { path ->
                    Picasso.get().load(File(path)).into(imgDetailMoviePoster)
                }
            } else {
                movieDetail.posterUrl?.let { url -> Picasso.get().load(url).into(imgDetailMoviePoster) }
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
            return MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_MOVIE_DETAIL_ID, movieId)
                }
            }
        }
    }
}