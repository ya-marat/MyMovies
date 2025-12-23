package com.example.mymovies.data.local.database.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.mymovies.data.local.database.entites.MovieDBEntity
import com.example.mymovies.data.local.database.entites.MoviePersonDBEntity
import com.example.mymovies.data.local.database.entites.MovieActorJoin

data class MovieActorRelation(

    @Embedded
    val movie: MovieDBEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = MovieActorJoin::class,
            parentColumn = "movieId",
            entityColumn = "actorId"
        )
    )
    val actors: List<MoviePersonDBEntity>
)
