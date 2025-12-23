package com.example.mymovies

class Consts {

    class General{
        companion object{
            const val TAG = "MyAppTag"
            const val USE_LOCAL_FILE_DATA = false
        }
    }

    class AppRequest{
        companion object {
            const val TAG = "AppRequest"
            const val BASE_URL = "https://api.poiskkino.dev/"
            const val KP_TOKEN = "KMKT0ZR-ABQMBF0-P13XZ4F-XY9VJM6"
            const val MOVIES_URL = "v1.4/movie"
            const val X_API_KEY_HEADER = "X-API-KEY:$KP_TOKEN"
        }

        class Queries {
            companion object{
                const val QUERY_PARAM_PAGE = "page"
            }
        }
    }

    class MovieParameters {
        companion object {
            const val LOADING_SIZE = 10
            const val PAGE = 3
            const val GENRE = "мелодрама"
        }
    }
}