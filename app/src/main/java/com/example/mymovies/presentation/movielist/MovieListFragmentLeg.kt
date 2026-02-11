package com.example.mymovies.presentation.movielist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mymovies.App
import com.example.mymovies.R
import com.example.mymovies.databinding.FragmentMovieHomeScreenBinding
import com.example.mymovies.domain.Movie
import com.example.mymovies.empty
import com.example.mymovies.presentation.ViewModelFactory
import com.example.mymovies.presentation.detailmovie.MovieDetailActivity
import com.example.mymovies.presentation.item.HorizontalItemDecoration
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListFragmentLeg : Fragment() {

    private val TAG = "MovieListFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MovieListViewModel

    private var _binding: FragmentMovieHomeScreenBinding? = null
    private val binding: FragmentMovieHomeScreenBinding
        get() = _binding ?: throw RuntimeException("FragmentMovieHomeScreenBinding is null")

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[MovieListViewModel::class.java]
        val firstAdapter = MovieAdapter()
        val secondListAdapter = MovieAdapter()
        val thirdListAdapter = MovieAdapter()

        firstAdapter.onMovieItemClick = ::onMovieClick
        firstAdapter.onEndPageReached = ::onEndPageReached

        secondListAdapter.onMovieItemClick = ::onMovieClick
        secondListAdapter.onEndPageReached = ::onEndPageReached

        thirdListAdapter.onMovieItemClick = ::onMovieClick
        thirdListAdapter.onEndPageReached = ::onEndPageReached

        val itemDecoration = HorizontalItemDecoration(20)

        binding.movieList1.adapter = firstAdapter
        binding.movieList2.adapter = secondListAdapter
        binding.movieList3.adapter = thirdListAdapter

        binding.tvListName3.text = getString(R.string.list_name_day_genre, viewModel.genre)

        binding.movieList1.addItemDecoration(itemDecoration)
        binding.movieList2.addItemDecoration(itemDecoration)
        binding.movieList3.addItemDecoration(itemDecoration)

        binding.firstMovieElement.setOnClickListener {
            viewModel.firstMovie?.let {
//                onMovieClick(it)
            }
        }

        lifecycleScope.launch {
            viewModel.state.collect { homeUIState ->

                Log.d(TAG, "State: $homeUIState")

                when (homeUIState) {
                    is MovieListUiState.Error -> {
                        binding.nsvRoot.visibility = View.GONE
                        binding.llErrorArea.visibility = View.VISIBLE
                        binding.pbLoadingMovie.visibility = View.GONE
                        binding.tvErrorText.text = getString(R.string.data_not_loaded)
//                        showDialog(homeUIState.message)
                    }

                    MovieListUiState.Loading -> {
                        binding.nsvRoot.visibility = View.GONE
                        binding.llErrorArea.visibility = View.GONE
                        binding.pbLoadingMovie.visibility = View.VISIBLE
                    }

                    is MovieListUiState.Success -> {
                        binding.pbLoadingMovie.visibility = View.GONE
                        binding.llErrorArea.visibility = View.GONE
                        binding.nsvRoot.visibility = View.VISIBLE

//                        homeUIState.firstMovie?.let { firstMovie ->
//                            firstMovie.urlPoster?.let {
//                                Picasso.get().load(it).into(binding.firstMovieElement)
//                            }
//                        }

//                        firstAdapter.submitList(homeUIState.newMovies)
//                        secondListAdapter.submitList(homeUIState.popularMovies)
//                        thirdListAdapter.submitList(homeUIState.genreMovies)
                    }

                    is MovieListUiState.Initial -> {}
                }
            }
        }
    }

    private fun onMovieClick(movie: Movie) {
        val intent =
            MovieDetailActivity.newIntent(
                requireActivity(),
                movie.name ?: String.Companion.empty(),
                movie.id
            )
        startActivity(intent)
    }

    private fun showDialog(text: String) {
        AlertDialog.Builder(requireActivity(), R.style.CustomAlertDialogTheme)
            .setTitle("Ошибка")
            .setMessage(text)
            .setPositiveButton("Да") { dialog, id ->
                {

                }
            }
            .setNegativeButton("Нет") { dialog, id ->
                {

                }
            }
            .show()
    }

    private fun onEndPageReached() {
        Log.d(TAG, "OnReached")
        //viewModel.loadMovies()
    }

    companion object {

        fun newInstance(): Fragment? {
            return null
        }
    }
}