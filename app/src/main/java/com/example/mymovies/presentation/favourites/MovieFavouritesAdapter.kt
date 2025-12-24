package com.example.mymovies.presentation.favourites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mymovies.databinding.ItemFavouriteMovieBinding
import com.example.mymovies.domain.Movie
import com.example.mymovies.presentation.adapter.MovieDiffCallback

class MovieFavouritesAdapter(

) : ListAdapter<FavouriteMovieUi, FavouriteMovieViewHolder>(FavouriteMovieDiffCallback()) {

    var onElementClick: ((favouriteMovieUi: FavouriteMovieUi) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteMovieViewHolder {
        val binding = ItemFavouriteMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return FavouriteMovieViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FavouriteMovieViewHolder,
        position: Int
    ) {
        val movie = getItem(position)
        with(holder.binding) {
            tvFavouriteName.text = movie.title

            root.setOnClickListener {
                onElementClick?.invoke(movie)
            }
        }
    }
}