package com.example.mymovies.presentation.favourites

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.mymovies.R
import com.example.mymovies.presentation.common.ProgressBarIndicator
import java.io.File

@Composable
fun MovieFavouriteScreen(
    viewModel: FavouriteMoviesViewModel,
    onItemClick: (movieId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = viewModel.favouriteFlowTest.collectAsState()

    when (val stateValue = state.value) {
        FavouritesUiState.Failure -> {

        }

        FavouritesUiState.Loading -> {
            ProgressBarIndicator()
        }

        is FavouritesUiState.Success -> {
            MovieFavouriteScreenContent(
                movieList =  stateValue.movieList,
                onItemClick = { onItemClick(it) },
                modifier = modifier)
        }
    }

}

@Preview
@Composable
fun MovieFavouriteScreenPreview() {

    val previewList = mutableListOf<FavouriteMovieUi>()

    for (i in 1..10) {
        previewList.add(
            FavouriteMovieUi(
                id = i,
                title = "Movie name $i",
                posterPath = ""
            )
        )
    }

    MovieFavouriteScreenContent(
        movieList = previewList,
        onItemClick = {}
    )
}

@Composable
fun MovieFavouriteScreenContent(
    movieList: List<FavouriteMovieUi>,
    onItemClick: (movieId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(movieList) { favouriteMovieUi ->
            FavouriteMovieItem(
                favouriteMovieUi,
                onItemClick= { onItemClick(it) }
            )
        }
    }
}

@Composable
private fun FavouriteMovieItem(
    favouriteMovieUi: FavouriteMovieUi,
    onItemClick: (movieId: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(105.dp)
            .background(Color.Black),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(105.dp)
                .padding(horizontal = 10.dp)
                .padding(start = 10.dp)
                .clickable{ onItemClick(favouriteMovieUi.id) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = File(favouriteMovieUi.posterPath),
                contentDescription = null,
                placeholder = painterResource(R.drawable.default_poster),
                error = painterResource(R.drawable.default_poster),
                fallback = painterResource(R.drawable.default_poster),
                modifier = Modifier
                    .width(60.dp)
                    .height(90.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 10.dp)
                    .padding(vertical = 5.dp)
                    .weight(2f)
            ) {
                Text(
                    text = favouriteMovieUi.title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier
                        .padding(top = 10.dp),
                    text = "2025",
                    color = colorResource(R.color.sub_text_color),
                    fontSize = 14.sp
                )
            }

            Box(
                modifier = Modifier
                    .width(50.dp)
                    .fillMaxHeight(),
                contentAlignment = Alignment.TopCenter
            ) {
                IconButton(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .size(30.dp),
                    onClick = {},
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_kebab),
                        contentDescription = null
                    )
                }
            }
        }
    }
}