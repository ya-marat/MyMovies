package com.example.mymovies.presentation.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.Consts
import com.example.mymovies.domain.common.Result
import com.example.mymovies.domain.usecases.GetMoviesByGenreUseCase
import com.example.mymovies.domain.usecases.GetMoviesUseCase
import com.example.mymovies.domain.usecases.GetPopularMoviesUseCase
import com.example.mymovies.presentation.MoviePresentationMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    private val moviePresentationMapper: MoviePresentationMapper
) : ViewModel() {

    companion object {
        private const val TAG = "MovieListViewModel"
    }

    private var _state = MutableStateFlow<MovieListUiState>(MovieListUiState.Initial)
    var state = _state.asStateFlow()

    init {
        loadMainPageMovies()
    }

    val genre = Consts.MovieParameters.GENRE
    var firstMovie: MovieItemUi? = null

    private fun loadMainPageMovies() {
        viewModelScope.launch {

            _state.value = MovieListUiState.Loading

            val currentPage = Consts.MovieParameters.PAGE //Todo Manager responsible

            val newLoadedMoviesResult = getMoviesUseCase.getMovies(currentPage)
            val popularMoviesResult = getPopularMoviesUseCase.loadPopularMovies(currentPage)
            val genreMoviesResult = getMoviesByGenreUseCase.getMoviesByGenre(currentPage, genre)

            val results = listOf(newLoadedMoviesResult, popularMoviesResult, genreMoviesResult)

            results.forEach {
                if (it is Result.Failure) {
                    _state.value = MovieListUiState.Error(
                        moviePresentationMapper.mapDomainErrorToMovieUiError(it.error)
                    )
                    return@launch
                }
            }

            val newMoviesData = (newLoadedMoviesResult as Result.Success).data

            if (newMoviesData.isNotEmpty()) {

                val firstMovieInMovies =
                    newMoviesData.firstOrNull { movie -> movie.localPathPoster != null || movie.urlPoster != null }

                firstMovieInMovies?.let {
                    firstMovie = moviePresentationMapper.mapDomainToMovieItemUi(firstMovieInMovies)
                }

                newMoviesData.toMutableList().remove(firstMovieInMovies)
            }

            val newMovies = newMoviesData.map { moviePresentationMapper.mapDomainToMovieItemUi(it) }
            val popularMovies = (popularMoviesResult as Result.Success).data.map {
                moviePresentationMapper.mapDomainToMovieItemUi(it)
            }
            val genreMovies = (genreMoviesResult as Result.Success).data.map {
                moviePresentationMapper.mapDomainToMovieItemUi(it)
            }

            _state.value = MovieListUiState.Success(
                firstMovie = firstMovie,
                newMovies = newMovies,
                popularMovies = popularMovies,
                genreMovies = genreMovies
            )
        }
    }
}