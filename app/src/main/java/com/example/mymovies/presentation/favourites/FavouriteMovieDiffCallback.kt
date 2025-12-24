package com.example.mymovies.presentation.favourites

import androidx.recyclerview.widget.DiffUtil
import com.example.mymovies.domain.Movie

class FavouriteMovieDiffCallback: DiffUtil.ItemCallback<FavouriteMovieUi>() {
    override fun areItemsTheSame(oldItem: FavouriteMovieUi, newItem: FavouriteMovieUi) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FavouriteMovieUi, newItem: FavouriteMovieUi) = oldItem == newItem
}