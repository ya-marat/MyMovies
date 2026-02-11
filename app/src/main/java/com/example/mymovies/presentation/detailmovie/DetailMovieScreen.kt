package com.example.mymovies.presentation.detailmovie

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import com.example.mymovies.R
import com.example.mymovies.empty
import java.io.File

@Composable
fun DetailMovieScreen(
    viewModel: MovieDetailViewModel
) {
    val state = viewModel.state.collectAsState()
    val favouriteAddOperationState = viewModel.favouriteMovieOperationUIStateFlow.collectAsState(
        FavouriteMovieOperationUIState.Initial
    )

    val message = when (favouriteAddOperationState.value) {
        FavouriteMovieOperationUIState.Initial -> String.empty()
        FavouriteMovieOperationUIState.AddFavouriteError -> stringResource(R.string.error_add_favourite_toast)
        FavouriteMovieOperationUIState.AddFavouriteSuccess -> stringResource(R.string.add_to_favourites_toast)
        FavouriteMovieOperationUIState.RemoveFavouriteError -> stringResource(R.string.error_remove_favourite_toast)
        FavouriteMovieOperationUIState.RemoveFavouriteSuccess -> stringResource(R.string.remove_from_favorites_toast)
    }

    if (message.isNotEmpty()) {
        Toast.makeText(LocalContext.current, message.toString(), Toast.LENGTH_SHORT).show()
    }

    when (val stateValue = state.value) {
        is DetailMovieUIState.Error -> {}
        DetailMovieUIState.Initial -> {}
        DetailMovieUIState.Loading -> {}
        is DetailMovieUIState.Success -> {
            DetailMovieScreenContent(
                detailUI = stateValue.movie,
                onFavouriteClick = { viewModel.onFavouriteClick() },
                onTrailerClick = {}
            )
        }
    }
}

@Composable
@Preview
fun DetailMovieScreenPreview() {
    DetailMovieScreenContent(
        detailUI = MovieDetailUI(
            id = 100,
            name = "Movie name",
            movieDetail = "2022 | 18+ | K-Drama",
            description = "A young woman, bullied to the point of deciding to drop out of school, plans the best way to get revenge. After becoming a primary school teacher, she takes in the son of the man who tormented her the most to enact her vengeance.",
            year = "2001",
            rating = "8.1",
            ageRating = "13",
            posterUrl = "poster_url",
            posterLocalPath = "local_poster_url",
            isFavourite = false,
            actors = "actor 1, actor 2, actor 3",
            creators = "creator 1, creator 2,",
            genres = "genre 1, genre 2, genre 3",
            trailers = listOf(
                MovieDetailTrailerUi(
                    trailerName = "TRAILER MOVIE 1",
                    trailerUrl = "URL"
                )
            )
        ),
        onFavouriteClick = {},
        onTrailerClick = {}
    )
}

@Composable
fun DetailMovieScreenContent(
    detailUI: MovieDetailUI,
    onFavouriteClick: () -> Unit,
    onTrailerClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.app_black))
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp)
            .verticalScroll(rememberScrollState()),
    ) {

        val (
            moviePosterImg,
            movieTitleText,
            favouriteBtn,
            moviePropertiesText,
            movieDescriptionText,
        ) = createRefs()

        val (
            movieDetailRows,
            movieTrailerArea,
            spacer
        ) = createRefs()

        val source: Any? = when {
            detailUI.posterLocalPath.isNotEmpty() -> File(detailUI.posterLocalPath)
            detailUI.posterUrl.isNotEmpty() -> detailUI.posterUrl
            else -> null
        }

        AsyncImage(
            model = source,
            contentDescription = null,
            placeholder = painterResource(R.drawable.default_poster),
            error = painterResource(R.drawable.default_poster),
            fallback = painterResource(R.drawable.default_poster),
            modifier = Modifier
                .width(200.dp)
                .height(300.dp)
                .constrainAs(moviePosterImg) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )


        IconButton(
            modifier = Modifier
                .constrainAs(favouriteBtn) {
                    end.linkTo(parent.end)
                    top.linkTo(moviePosterImg.bottom)
                },
            onClick = { onFavouriteClick() }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_favourites_btn),
                tint = if (detailUI.isFavourite) colorResource(R.color.main_color_2) else Color.White,
                contentDescription = null
            )
        }

        Text(
            text = detailUI.name,
            modifier = Modifier
                .wrapContentHeight()
                .constrainAs(movieTitleText) {
                    top.linkTo(moviePosterImg.bottom, margin = 10.dp)
                    end.linkTo(favouriteBtn.start, margin = 25.dp)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints
                },
            color = Color.White,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
        )

        Text(
            text = detailUI.movieDetail,
            color = colorResource(R.color.sub_text_color),
            fontSize = 12.sp,
            modifier = Modifier
                .padding(top = 10.dp)
                .constrainAs(moviePropertiesText) {
                    top.linkTo(movieTitleText.bottom)
                }
        )

        Text(
            text = detailUI.description ?: String.empty(),
            modifier = Modifier
                .constrainAs(movieDescriptionText) {
                    top.linkTo(moviePropertiesText.bottom)
                }
                .wrapContentHeight()
                .padding(top = 10.dp),
            color = Color.White,
            fontSize = 15.sp,
            lineHeight = 17.sp
        )

        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .constrainAs(movieDetailRows) {
                    top.linkTo(movieDescriptionText.bottom)
                }
        ) {
            LabelAndText(
                label = stringResource(R.string.starring_label),
                text = detailUI.actors
            )

            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            LabelAndText(
                label = stringResource(R.string.creators_label),
                text = detailUI.creators
            )

            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            LabelAndText(
                label = stringResource(R.string.genre_label),
                text = detailUI.genres
            )

            if (detailUI.trailers.isNotEmpty()) {

                val trailerData = detailUI.trailers.first()

                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                ) {
                    Text(
                        text = stringResource(R.string.trailer_lbl),
                        color = colorResource(R.color.detail_text_color),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = trailerData.trailerName ?: String.empty(),
                        modifier = Modifier
                            .padding(top = 10.dp),
                        color = colorResource(R.color.detail_text_color),
                        fontSize = 14.sp
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp),
                            colors = IconButtonDefaults.iconButtonColors()
                                .copy(contentColor = Color.White),
                            onClick = { onTrailerClick() }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.video_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
    }
}

@Composable
fun LabelAndText(
    label: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = label,
            color = colorResource(R.color.detail_text_color),
            fontSize = 15.sp
        )

        Spacer(
            modifier = Modifier
                .width(10.dp)
        )

        Text(
            text = text,
            color = colorResource(R.color.detail_text_color),
            fontSize = 15.sp
        )
    }
}