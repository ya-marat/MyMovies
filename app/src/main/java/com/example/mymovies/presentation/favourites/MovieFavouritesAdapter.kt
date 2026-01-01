package com.example.mymovies.presentation.favourites

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import com.example.mymovies.R
import com.example.mymovies.databinding.ItemFavouriteMovieBinding
import com.squareup.picasso.Picasso
import java.io.File

class MovieFavouritesAdapter(
    val context: Context
) : ListAdapter<FavouriteMovieUi, FavouriteMovieViewHolder>(FavouriteMovieDiffCallback()) {

    var onElementClick: ((favouriteMovieUi: FavouriteMovieUi) -> Unit)? = null
    var onRemoveMovieClick: ((Int) -> Unit)? = null

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
        val favouriteMovie = getItem(position)
        with(holder.binding) {
            tvFavouriteName.text = favouriteMovie.title

            root.setOnClickListener {
                onElementClick?.invoke(favouriteMovie)
            }
        }

        favouriteMovie.posterPath?.let { posterPath ->
            Picasso.get().load(File(posterPath)).into(holder.binding.ivFavouriteMoviePoster)
        }

        holder.binding.ibFavMovieItemMenu.setOnClickListener {
            val contextThemeWrapper = ContextThemeWrapper(context, R.style.CustomPopupMenu)
            val popupMenu = PopupMenu(contextThemeWrapper, holder.binding.ibFavMovieItemMenu)
            popupMenu.inflate(R.menu.favourite_item_menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                onRemoveMovieClick?.invoke(favouriteMovie.id)
                return@setOnMenuItemClickListener false

            }

            popupMenu.show()
        }
    }
}