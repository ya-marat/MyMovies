package com.example.mymovies.data.local.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymovies.data.local.database.dao.DatabaseDao
import com.example.mymovies.data.local.database.entites.MovieDBEntity
import com.example.mymovies.data.local.database.entites.MovieActorDBEntity
import com.example.mymovies.data.local.database.entites.MovieActorJoin
import com.example.mymovies.data.local.database.entites.MovieGenreDBEntity

@Database(
    entities = [
        MovieDBEntity::class,
        MovieActorDBEntity::class,
        MovieGenreDBEntity::class,
        MovieActorJoin::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private var db: AppDatabase? = null
        private const val DB_NAME = "my_movies.db"
        private val LOCK = Any()

        fun getInstance(application: Application): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun favouriteMoviesDao(): DatabaseDao
}