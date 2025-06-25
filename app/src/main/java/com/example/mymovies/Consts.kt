package com.example.mymovies

class Consts {
    companion object {
        const val KP_TOKEN = "KMKT0ZR-ABQMBF0-P13XZ4F-XY9VJM6"
        const val MOVIES_URL = "movie?token=${KP_TOKEN}&&rating.kp=4-8&sortField=votes.kp&sortType=-1&limit=30"
    }
}