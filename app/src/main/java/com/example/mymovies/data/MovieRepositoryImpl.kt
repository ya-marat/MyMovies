package com.example.mymovies.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.mymovies.data.local.database.dto.MovieCastDto
import com.example.mymovies.data.local.database.entites.MovieActorJoin
import com.example.mymovies.data.local.database.entites.MovieGenreDBEntity
import com.example.mymovies.data.local.database.entites.MoviePersonDBEntity
import com.example.mymovies.data.local.datasource.LocalDataSource
import com.example.mymovies.data.mapper.MovieMapper
import com.example.mymovies.data.remote.datasource.ApiResult
import com.example.mymovies.data.remote.datasource.RemoteDataSource
import com.example.mymovies.domain.ImageManager
import com.example.mymovies.domain.Movie
import com.example.mymovies.domain.MovieRepository
import com.example.mymovies.domain.common.DomainError
import com.example.mymovies.domain.common.Result
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val mapper: MovieMapper,
    private val imageManager: ImageManager,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : MovieRepository {

    override suspend fun loadMovies(page: Int): Result<List<Movie>> {
        return safeApiCall(
            apiCall = { remoteDataSource.getMovies(page) },
            transform = { dto -> dto.map { mapper.mapDtoToDomain(it) } }
        )
    }

    override suspend fun loadMovieById(movieId: Int): Result<Movie> {

        val dbMovieResult = safeDbCall { localDataSource.getMovieFromDb(movieId) }

        return when (dbMovieResult) {
            is Result.Failure -> {
                dbMovieResult
            }

            is Result.Success -> {

                val movieDBEntity = dbMovieResult.data

                if (movieDBEntity == null) {
                    val apiResult = safeApiCall(
                        apiCall = { remoteDataSource.getMovieById(movieId) },
                        transform = { dto -> mapper.mapDtoToDomain(dto) }
                    )

                    return apiResult
                } else {
                    var moviePersonsDto: List<MovieCastDto> = listOf()
                    var movieGenresDb: List<MovieGenreDBEntity> = listOf()

                    val moviePersonsDbResult =
                        safeDbCall { localDataSource.getMovieActorsCast(movieId) }

                    if (moviePersonsDbResult is Result.Success) {
                        moviePersonsDbResult.data.let { moviePersonsDto = it }
                    }

                    val movieGenresDbResult = safeDbCall { localDataSource.getMovieGenres(movieId) }

                    if (movieGenresDbResult is Result.Success) {
                        movieGenresDbResult.data.let { movieGenresDb = movieGenresDbResult.data }
                    }

                    val movie = mapper.mapMovieDbToMovie(
                        movieDBEntity,
                        moviePersonsDto,
                        movieGenresDb
                    )

                    Result.Success(movie)
                }
            }
        }
    }

    override suspend fun loadNewMovies(page: Int): Result<List<Movie>> {
        return safeApiCall(
            apiCall = { remoteDataSource.getNewMovies(page) },
            transform = { dto -> dto.map { mapper.mapDtoToDomain(it) } }
        )
    }

    override suspend fun loadPopularMovies(page: Int): Result<List<Movie>> {
        return safeApiCall(
            apiCall = { remoteDataSource.getPopularMovies(page) },
            transform = { dto -> dto.map { mapper.mapDtoToDomain(it) } }
        )
    }

    override suspend fun loadMoviesByGenre(
        page: Int,
        genre: String
    ): Result<List<Movie>> {
        return safeApiCall(
            apiCall = { remoteDataSource.getMoviesByGenre(page, genre) },
            transform = { dto -> dto.map { mapper.mapDtoToDomain(it) } }
        )
    }

    //TODO put it in Transaction in next version
    override suspend fun insertMovieToDb(movie: Movie): Result<Unit> {

        val movieDbEntity = mapper.mapMovieToMovieDb(
            movie = movie,
            posterPath = movie.urlPoster?.let {
                imageManager.downloadAndSaveMoviePoster(movie.urlPoster, movie.id)
            } ?: ""
        )

        var movieGenres = listOf<MovieGenreDBEntity>()
        var moviePersons = listOf<MoviePersonDBEntity>()
        var joinList = listOf<MovieActorJoin>()

        movie.moviePersons?.let {
            moviePersons = mapper.mapMoviePersonsDomainToMoviePersonsDb(movie.moviePersons)

            joinList = moviePersons.map {
                MovieActorJoin(
                    movieId = movie.id,
                    actorId = it.id,
                    role = "",
                    order = 0
                )
            }
        }

        movie.genres?.let {
            movieGenres = mapper.mapMovieGenresToMovieGenresDb(movie.id, movie.genres)
        }

        return safeDbCall {
            localDataSource.saveMovie(movieDbEntity)

            if (moviePersons.isNotEmpty())
                localDataSource.saveMoviePersons(moviePersons)

            if (joinList.isNotEmpty())
                localDataSource.saveMovieActorJoins(joinList)

            if (movieGenres.isNotEmpty())
                localDataSource.saveMovieGenres(movieGenres)
        }
    }

    override suspend fun getMovieFromDb(movieId: Int): Result<Movie> {
        TODO()
    }

    override fun observeIsFavourite(movieId: Int): LiveData<Boolean> {
        return localDataSource.observeIsFavourite(movieId)
    }

    override fun observeFavourites(): LiveData<Result<List<Movie>>> {
//        val result = MediatorLiveData<Result<List<Movie>>>()
//
//        result.addSource(localDataSource.observeFavourite()) { entities ->
//            try {
//                val favouriteMovies = entities.map { mapper.mapMovieDbToMovie(it) }
//                result.value = Result.Success(favouriteMovies)
//            } catch (e: Exception) {
//                result.value = Result.Failure(DomainError.Unknown(e))
//            }
//        }

        return localDataSource.observeFavourite().map { entities ->
            try {
                val mappedData = entities.map { it ->
                    mapper.mapMovieDbToMovie(it)
                }
                Result.Success(mappedData)
            } catch (e: Exception) {
                Result.Failure(DomainError.Unknown(e))
            }
        }
    }

    override suspend fun removeMovieFromDb(movie: Movie): Result<Unit> {

        val movieId = movie.id
        val result = safeDbCall { localDataSource.removeMovie(movieId) }

        if (result is Result.Success && movie.localPathPoster != null) {
            imageManager.removeImagePoster(movie.localPathPoster)
        }

        return result
    }

    override suspend fun loadFavouriteMovies(): Result<List<Movie>> {
        return safeDbGetDataCall(
            dbCall = { localDataSource.getMoviesFromDb() },
            transform = { list -> list.map { dbMovie -> mapper.mapMovieDbToMovie(dbMovie) } }
        )
    }

    private suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> ApiResult<T>,
        transform: (T) -> R
    ): Result<R> {
        return when (val callResult = apiCall()) {
            is ApiResult.Success -> Result.Success(transform(callResult.data))
            is ApiResult.NetworkError -> Result.Failure(DomainError.NoInternet)
            is ApiResult.HttpError -> {
                val domainError = when (callResult.code) {
                    404 -> DomainError.NotFound
                    else -> DomainError.Server(callResult.code, callResult.message)
                }
                Result.Failure(domainError)
            }

            is ApiResult.UnknownError -> Result.Failure(DomainError.Unknown(callResult.e))
        }
    }

    private suspend fun <T> safeDbCall(
        dbMethodCall: suspend () -> T
    ): Result<T> {
        return try {
            Result.Success(dbMethodCall())
        } catch (e: Exception) {
            Result.Failure(DomainError.Unknown(e))
        }
    }

    private suspend fun <T, R> safeDbGetDataCall(
        dbCall: suspend () -> T,
        transform: (T) -> R
    ): Result<R> {
        return try {
            val dbCallResult = dbCall()
            if (dbCallResult != null) {
                Result.Success(transform(dbCallResult))
            } else {
                Result.Failure(DomainError.NotFound)
            }
        } catch (e: Exception) {
            Result.Failure(DomainError.Unknown(e))
        }
    }

    private fun <T, R> safeDbObserveCall(
        dbCall: () -> LiveData<T>,
        transform: (T) -> R
    ): LiveData<Result<R>> {
        return dbCall().map { dbResult ->
            try {
                Result.Success(transform(dbResult))
            } catch (e: Exception) {
                Result.Failure(DomainError.Unknown(e))
            }
        }
    }


    companion object {
        private const val TAG = "MovieRepositoryImpl"
    }
}