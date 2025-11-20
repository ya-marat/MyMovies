package com.example.mymovies.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.R
import com.example.mymovies.databinding.FragmentMovieHomeScreenBinding
import com.example.mymovies.domain.Movie
import com.example.mymovies.presentation.App
import com.example.mymovies.presentation.viewmodels.MovieListViewModel
import com.example.mymovies.presentation.ViewModelFactory
import com.example.mymovies.presentation.activities.MovieDetailActivity
import com.example.mymovies.presentation.adapter.MovieAdapter
import com.example.mymovies.presentation.item.HorizontalItemDecoration
import com.squareup.picasso.Picasso
import javax.inject.Inject

class MovieListFragment : Fragment() {

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
        val firstAdapter = MovieAdapter(requireActivity())
        val secondListAdapter = MovieAdapter(requireActivity())
        val thirdListAdapter = MovieAdapter(requireActivity())

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

        viewModel.movies.observe(viewLifecycleOwner) {
            firstAdapter.submitList(it)
        }

        viewModel.firstMovieElement.observe(viewLifecycleOwner) {
            Picasso.get().load(it.poster).into(binding.firstMovieElement)
        }

        viewModel.popularMovies.observe(viewLifecycleOwner) {
            secondListAdapter.submitList(it)
        }

        viewModel.moviesByGenre.observe(viewLifecycleOwner) {
            thirdListAdapter.submitList(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.pbLoadingMovie.visibility = if(it) View.VISIBLE else View.GONE
            binding.nsvRoot.visibility = if(!it) View.VISIBLE else View.GONE
        }

        binding.firstMovieElement.setOnClickListener {
            viewModel.firstMovieElement.value?.let {
                onMovieClick(it)
            }
        }
    }

    private fun onMovieClick(movie: Movie) {
        val intent = MovieDetailActivity.newIntent(requireActivity(), movie)
        startActivity(intent)
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