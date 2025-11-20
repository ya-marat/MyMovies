package com.example.mymovies.domain

enum class MovieProfessionType(val pr: String?) {
    NONE(null),
    ACTOR("actor"),
    DIRECTOR("director"),
    PRODUCER("producer");

    companion object{
        fun professionTypeByStr(typeName: String?): MovieProfessionType? {
            return entries.find { it.pr == typeName }
        }
    }
}