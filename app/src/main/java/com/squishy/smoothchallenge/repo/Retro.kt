package com.squishy.smoothchallenge.repo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retro {

    const val BASE_URL = "https://www.colourlovers.com/api/"

    private val INSTANCE : Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    val API : Api by lazy {
        INSTANCE.create(Api::class.java)
    }
}