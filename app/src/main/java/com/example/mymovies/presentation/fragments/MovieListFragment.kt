package com.example.mymovies.presentation.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.databinding.FragmentMovieHomeScreenBinding
import com.example.mymovies.domain.Movie
import com.example.mymovies.presentation.App
import com.example.mymovies.presentation.viewmodels.MovieListViewModel
import com.example.mymovies.presentation.NavigateFragment
import com.example.mymovies.presentation.ViewModelFactory
import com.example.mymovies.presentation.activities.MovieDetailActivity
import com.example.mymovies.presentation.adapter.MovieAdapter
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
        val adapter = MovieAdapter(requireActivity())

        adapter.onMovieItemClick = ::onMovieClick
        adapter.onEndPageReached = ::onEndPageReached

        binding.movieList.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            Log.d(TAG, "Loaded Movies ${it.size}}")
        }

        viewModel.loadMovies()
    }

    private fun onMovieClick(movie: Movie) {
        Log.d(TAG, "Click on movie ${movie.id} ${movie.name}")

        val detailFragment = MovieDetailFragment.newInstance(movie)

        val requireActivity = requireActivity();

//        if(requireActivity is NavigateFragment){
//            with(requireActivity) { navigateToFragmentWithParameter(detailFragment) }
//        }

        val intent = MovieDetailActivity.newIntent(requireActivity, movie)
        startActivity(intent)
    }

    private fun onEndPageReached(){
        Log.d(TAG, "OnReached")
        //viewModel.loadMovies()
    }

    companion object {

        fun newInstance(): Fragment? {
            return null
        }
    }
}