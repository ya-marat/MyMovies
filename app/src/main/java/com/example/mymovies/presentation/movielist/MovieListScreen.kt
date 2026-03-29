package com.example.mymovies.presentation.movielist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.mymovies.R
import com.example.mymovies.di.LocalViewModelFactory
import com.example.mymovies.empty
import com.example.mymovies.presentation.ViewModelFactory
import com.example.mymovies.presentation.common.AppAlertDialog
import com.example.mymovies.presentation.common.ErrorContent
import com.example.mymovies.presentation.common.ProgressBarIndicator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MovieListScreen(
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val factory = LocalViewModelFactory.current
    val viewModel: MovieListViewModel = viewModel(factory = factory)
    val state = viewModel.state.collectAsState()
    val genreState = viewModel.genreListState.collectAsState()


    when (val stateValue = state.value) {
        is MovieListUiState.Error -> {
            var showDialog by remember(stateValue) { mutableStateOf(true) }

            ErrorContent(
                errorText = stringResource(R.string.error_load),
                buttonText = stringResource(R.string.refresh),
                onRefreshClick = { viewModel.loadMainPageMovies() }
            )

            if (showDialog) {
                AppAlertDialog(
                    onDismiss = { showDialog = false },
                    title = stringResource(R.string.error_label),
                    text = stringResource(R.string.error_load_dialog),
                )
            }
        }

        MovieListUiState.Initial -> {}
        MovieListUiState.Loading -> {
            ProgressBarIndicator(
                modifier = Modifier.fillMaxSize()
            )
        }

        is MovieListUiState.Success -> {
            MovieListScreenContent(
                firstMovie = stateValue.firstMovie,
                newMovies = stateValue.newMovies,
                popularMovies = stateValue.popularMovies,
                genreListUIState = genreState.value,
                onItemClick = { onItemClick(it.id) },
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
private fun MovieListScreenContentPreview() {
    val firstMovie = MovieItemUi(0, String.empty(), String.empty())

    val newMovies = mutableListOf<MovieItemUi>()

    for (i in 1..10) {
        newMovies.add(
            MovieItemUi(
                i,
                String.empty(),
                String.empty()
            )
        )
    }

    val popularMovies = mutableListOf<MovieItemUi>()

    for (i in 1..10) {
        popularMovies.add(
            MovieItemUi(
                i,
                String.empty(),
                String.empty()
            )
        )
    }

    val genreMovies = mutableListOf<MovieItemUi>()

    for (i in 1..10) {
        popularMovies.add(
            MovieItemUi(
                i,
                String.empty(),
                String.empty()
            )
        )
    }

    MovieListScreenContent(
        firstMovie,
        newMovies,
        popularMovies,
        MovieGenreListUIState.Initial,
        onItemClick = { }
    )
}

@Composable
private fun MovieListScreenContent(
    firstMovie: MovieItemUi?,
    newMovies: List<MovieItemUi>,
    popularMovies: List<MovieItemUi>,
    genreListUIState: MovieGenreListUIState,
    onItemClick: (MovieItemUi) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.app_black))
    ) {

        item {
            AsyncImage(
                model = firstMovie?.posterUrl,
                contentDescription = null,
                placeholder = painterResource(R.drawable.default_poster),
                error = painterResource(R.drawable.default_poster),
                fallback = painterResource(R.drawable.default_poster),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .clickable(onClick = { firstMovie?.let { onItemClick(firstMovie) } })
            )
        }

        item {
            MovieCategoryItem(
                categoryName = stringResource(R.string.list_name_new_movies),
                movies = newMovies,
                modifier = Modifier.padding(top = 20.dp),
                onItemClick = { onItemClick(it) })

            MovieCategoryItem(
                categoryName = stringResource(R.string.list_name_popular),
                movies = popularMovies,
                modifier = Modifier.padding(top = 20.dp),
                onItemClick = { onItemClick(it) })



            when (genreListUIState) {
                MovieGenreListUIState.Initial -> {}
                is MovieGenreListUIState.Error -> {
                    Text(text = "Error")
                }

                MovieGenreListUIState.Loading -> {
                    ProgressBarIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        progressIndicatorSize = 48,
                        strokeWidth = 5
                    )
                }

                is MovieGenreListUIState.Success -> {
                    val genreMovies = genreListUIState.genreMovies

                    MovieCategoryItem(
                        categoryName = stringResource(
                            R.string.list_name_day_genre,
                            genreListUIState.genreName
                        ),
                        movies = genreMovies,
                        modifier = Modifier.padding(top = 20.dp),
                        onItemClick = { onItemClick(it) })
                }
            }
        }

        item {
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
        }
    }
}

@Composable
private fun MovieCategoryItem(
    categoryName: String,
    movies: List<MovieItemUi>,
    modifier: Modifier = Modifier,
    onItemClick: (MovieItemUi) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = categoryName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(bottom = 10.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = movies,
                key = { it -> it.id },
            ) { item ->
                MovieItem(
                    item = item,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}

@Composable
private fun MovieItem(item: MovieItemUi, onClick: (MovieItemUi) -> Unit) {

    AsyncImage(
        model = item.posterUrl,
        contentDescription = null,
        placeholder = painterResource(R.drawable.default_poster),
        error = painterResource(R.drawable.default_poster),
        fallback = painterResource(R.drawable.default_poster),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(120.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(10))
            .clickable(onClick = { onClick(item) })
    )
}