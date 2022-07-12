package com.example.adadogsrestapi.network

import android.content.Context
import android.content.SharedPreferences
import com.example.adadogsrestapi.SHARED_PREFERENCES_FILE_NAME
import com.example.adadogsrestapi.storage.SharedPreferencesStorage
import com.example.adadogsrestapi.storage.Storage
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitGenerator {

    companion object {
        fun getInstance(context: Context): Retrofit{
            val loggingInterceptor = HttpLoggingInterceptor()
            val jwtInterceptor = JWTInterceptor(SharedPreferencesStorage(context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)))
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(jwtInterceptor)
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

        fun getrandomImageService(context: Context): DogsImageService {
            return getInstance(context).create(DogsImageService::class.java)
        }
    }

    class JWTInterceptor(private val tokenStorage: Storage) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
            val token = tokenStorage.getToken()
            if (token != null) {
                request.addHeader("Authorization", "Bearer $token")
            }
            return chain.proceed(request.build())
        }

    }
}