package com.example.stocktracker24
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("markets/options/most-active?type=STOCKS")
    suspend fun getMostActiveStocks(
        @Header("x-rapidapi-key") apiKey: String,
        @Header("x-rapidapi-host") apiHost: String
    ): Response<StockResponse>
}