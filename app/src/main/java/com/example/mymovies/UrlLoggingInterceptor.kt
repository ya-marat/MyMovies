package com.example.mymovies

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.StringBuilder
import java.nio.charset.Charset

class UrlLoggingInterceptor : Interceptor {

    companion object{
        private const val TAG = "UrlLoggingInterceptor"
    }

    val sb = StringBuilder()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        sb.clear()

        // Логируем полный URL с параметрами
        val url = request.url
        sb.append("Url: ${url}\n")
        sb.append("Header: ${request.headers}")
        sb.append("Full Url: ${url.scheme}://${url.host}${url.encodedPath}\n")
        sb.append("Host: ${url.host}\n")
        sb.append("EncodedPath: ${url.encodedPath}\n")

//        println("🔄 REQUEST URL: ${url}")
//        println("🔄 FULL URL: ${url.scheme()}://${url.host()}--${url.encodedPath()}")

        sb.append("Params:\n")

        // Логируем query параметры

        for(p in url.queryParameterNames){



            val paramValues = url.queryParameterValues(p).orEmpty()
            //println("📋 PARAM: $paramName = ${paramValues.joinToString()}")
            sb.append("📋 PARAM: $p = ${paramValues.joinToString()}\n")
        }


        // Логируем headers
//        request.headers(). { header ->
//            println("📨 HEADER: ${header.first} = ${header.second}")
//        }

        //println("Header ${request.headers()}")
        val response = chain.proceed(request)

        val source = response.body?.source()
        source?.request(Long.MAX_VALUE)
        val buffer = source?.buffer
        val rawResponse = buffer?.clone()?.readString(Charset.forName("UTF-8"))

        sb.append("Response: Code: ${response.code}\n");
        sb.append("Response: Body: ${rawResponse?.take(500)}\n");


        Log.d(TAG, sb.toString())



        return response
    }
}