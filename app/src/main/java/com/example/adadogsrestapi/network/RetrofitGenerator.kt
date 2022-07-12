package com.example.adadogsrestapi.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitGenerator {

    companion object {
        fun getInstance(): Retrofit{
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                // .addInterceptor(jwtInterceptor)
                .writeTimeout(0, TimeUnit.MILLISECONDS)
                .readTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES).build()

            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
                .create()

            return Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

        }

        fun getrandomImageService(): DogsImageService {
            return getInstance().create(DogsImageService::class.java)
        }
    }
}