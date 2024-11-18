package com.example.stocktracker24


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://yahoo-finance15.p.rapidapi.com/api/v1/ ")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
