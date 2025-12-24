package com.example.mymovies.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mymovies.databinding.ItemMovieBinding
import com.example.mymovies.domain.Movie
import com.squareup.picasso.Picasso

class MovieAdapter() : ListAdapter<Movie, MovieViewHolder>(MovieDiffCallback()) {

    lateinit var onMovieItemClick: (m: Movie) -> Unit
    var onEndPageReached: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        with(holder.binding) {
            tvMovieRating.text = movie.rating.toString()
            movie.previewPoster?.let { Picasso.get().load(it).into(imgMoviePoster) }

            //Picasso.get().load(movie.poster).into(imgMoviePoster)
            root.setOnClickListener {
                onMovieItemClick(movie)
            }

            Log.d("MovieAdapter", "CurrentList ${currentList?.size}")
            if (position >= currentList.size - 10) {
                onEndPageReached?.invoke()
            }
        }
    }
}