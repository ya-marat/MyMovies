package com.example.mymovies

class Consts {

    class General{
        companion object{
            const val TAG = "MyAppTag"
        }
    }

    class AppRequest{
        companion object {
            const val TAG = "AppRequest"
            const val KP_TOKEN = "KMKT0ZR-ABQMBF0-P13XZ4F-XY9VJM6"
            const val MOVIES_URL = "movie"
            const val X_API_KEY_HEADER = "X-API-KEY:$KP_TOKEN"
        }

        class Queries {
            companion object{
                public const val QUERY_PARAM_PAGE = "page"
            }
        }
    }
}