package com.example.mymovies.presentation.fragments

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.R
import com.example.mymovies.databinding.FragmentMovieDetailBinding
import com.example.mymovies.domain.ImageManager
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MoviePerson
import com.example.mymovies.domain.MovieProfessionType
import com.example.mymovies.domain.MovieTrailer
import com.example.mymovies.presentation.App
import com.example.mymovies.presentation.viewmodels.MovieDetailViewModel
import com.example.mymovies.presentation.ViewModelFactory
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.File
import java.lang.Exception
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

        viewModel.movie.observe(viewLifecycleOwner) { loadedMovie ->
            Log.d(
                TAG,
                "MovieId ${loadedMovie?.id} ${loadedMovie?.name} ${loadedMovie?.moviePersons?.joinToString { "${it.name} = ${it.professionType}" }}"
            )
            bindMovieToView(loadedMovie)
        }

        movie.id?.let {
            binding.pbLoadingMovie.visibility = View.VISIBLE
            binding.clRoot.visibility = View.INVISIBLE
            viewModel.loadMovieById(it)
        }

        binding.ibFavourites.setOnClickListener {
            imageManager.trySaveImage(binding.imgDetailMoviePoster, movie.id!!)
            val color = ContextCompat.getColor(requireContext(), R.color.main_color_2)
            binding.ibFavourites.setColorFilter(color)
        }
    }

    private fun bindMovieToView(movie: Movie?) {
        if (movie == null) {
            return
        }

        val testPath = "/data/data/com.example.mymovies/files/movies_img_10085305.png"
        val posterFile = File(testPath)

        with(binding) {

            binding.pbLoadingMovie.visibility = View.GONE
            binding.clRoot.visibility = View.VISIBLE
            movie.poster?.let { it ->
                Picasso.get().load(it).into(imgDetailMoviePoster, object : Callback{
                    override fun onSuccess() {
                        Log.d(TAG, "Succes")
                    }

                    override fun onError(e: Exception?) {
                        Log.d(TAG, e.toString())
                    }
                })
            }
            tvMovieName.text = movie.name
            val detailMovieText = "${movie.year} | ${movie.rating}"
            tvDetailMovie.text = detailMovieText
            tvMovieDescription.text =
                movie.description
                    ?: getString(R.string.description_movie_detail_is_empty)
            val actors =
                movie.moviePersons?.filter { it.professionType == MovieProfessionType.ACTOR }
            tvStarringValue.text = convertPersonsToLine(actors)
            val creators =
                movie.moviePersons?.filter { it.professionType != MovieProfessionType.ACTOR }
            tvCreatorsValue.text = convertPersonsToLine(creators)
            val genres = movie.genres?.map { it?.name }
            tvGenreValue.text = genres?.joinToString(", ")

            prepareTrailer(movie.movieTrailers)

        }
    }

    private fun convertPersonsToLine(moviePersons: List<MoviePerson>?): String? {
        return moviePersons?.take(10)?.map {

            if (!it.name.isNullOrBlank())
                it.name
            else it.enName

        }?.joinToString(", ")
    }

    private fun prepareTrailer(movieTrailers: List<MovieTrailer>?) {
        if (movieTrailers.isNullOrEmpty()) {
            binding.llTrailerSectionGroup.visibility = View.GONE
            return
        }

        val trailer = movieTrailers.first()
        binding.llTrailerSectionGroup.visibility = View.VISIBLE
        binding.tvTrailerName.text = trailer.name
        binding.trailerView.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(trailer.url)
            }
            startActivity(intent)
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