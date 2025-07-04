package com.example.mymovies.data.network

import android.util.Log
import com.example.mymovies.Consts
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Logger
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://api.kinopoisk.dev/v1.4/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private fun createRetrofit(): Retrofit {

        val logging = HttpLoggingInterceptor {
            Log.d(Consts.AppRequest.TAG, it)
        }
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val client = OkHttpClient().newBuilder()
        client.addInterceptor(logging)

        return Retrofit.Builder()
           .addConverterFactory(GsonConverterFactory.create())
           .baseUrl(BASE_URL)
           .client(client.build())
           .build()
    }

    val apiService: ApiService = createRetrofit().create(ApiService::class.java)
}