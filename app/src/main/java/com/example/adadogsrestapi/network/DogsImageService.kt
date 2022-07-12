package com.example.adadogsrestapi.network

import retrofit2.Response
import retrofit2.http.GET

interface DogsImageService {
    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): Response<RandomDogImageDto>

    @GET("breeds/list/all")
    suspend fun getDogBreedsList(): Response<DogsListDto>
}