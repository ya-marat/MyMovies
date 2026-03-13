package com.example.mymovies.presentation.favourites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.mymovies.R
import com.example.mymovies.di.LocalViewModelFactory
import com.example.mymovies.presentation.ViewModelFactory
import com.example.mymovies.presentation.common.ProgressBarIndicator
import java.io.File

@Composable
fun MovieFavouriteScreen(
    onItemClick: (movieId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val factory = LocalViewModelFactory.current
    val viewModel: FavouriteMoviesViewModel = viewModel(factory = factory)
    val state = viewModel.favouriteFlowTest.collectAsState()

    when (val stateValue = state.value) {
        FavouritesUiState.Failure -> {

        }

        FavouritesUiState.Loading -> {
            ProgressBarIndicator()
        }

        is FavouritesUiState.Success -> {
            MovieFavouriteScreenContent(
                movieList = stateValue.movieList,
                onItemClick = { movieId -> onItemClick(movieId) },
                onRemoveClick = { movieId -> viewModel.removeFavourite(movieId) },
                modifier = modifier
            )
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
                title = "Movie name аdf $i",
                posterPath = ""
            )
        )
    }

    MovieFavouriteScreenContent(
        movieList = previewList,
        onItemClick = {},
        onRemoveClick = {}
    )
}

@Composable
fun MovieFavouriteScreenContent(
    movieList: List<FavouriteMovieUi>,
    onItemClick: (movieId: Int) -> Unit,
    onRemoveClick: (movieId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(
            items = movieList,
            key = { it.id }
        ) { favouriteMovieUi ->
            FavouriteMovieItem(
                favouriteMovieUi,
                onItemClick = { onItemClick(it) },
                onRemoveClick = { onRemoveClick(it) }
            )
        }
    }
}

@Composable
private fun FavouriteMovieItem(
    favouriteMovieUi: FavouriteMovieUi,
    onItemClick: (movieId: Int) -> Unit,
    onRemoveClick: (movieId: Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

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
                .clickable { onItemClick(favouriteMovieUi.id) },
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
                    onClick = { expanded = !expanded },
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_kebab),
                        contentDescription = null
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(colorResource(R.color.app_black)),
                        containerColor = colorResource(R.color.main_color_2),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        DropDownMenuItem(
                            itemName = stringResource(R.string.favourite_remove),
                            onItemClick = { onRemoveClick(favouriteMovieUi.id) }
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun DropDownMenuItem(
    itemName: String,
    onItemClick: () -> Unit
) {
    DropdownMenuItem(
        text = {
            Text(
                itemName,
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center
            )
        },
        onClick = { onItemClick() },
        colors = MenuDefaults.itemColors().copy(
            textColor = colorResource(R.color.main_color_2)
        ),
    )
}