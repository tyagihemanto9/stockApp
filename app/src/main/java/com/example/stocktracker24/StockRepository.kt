package com.example.stocktracker24

import android.util.Log

class StockRepository {
    private val apiService = ApiClient.instance

    suspend fun getMostActiveStocks(apiKey: String, apiHost: String): StockResponse? {
        val response = apiService.getMostActiveStocks(apiKey, apiHost)
        return if (response.isSuccessful) {
            Log.d("Repository", "Response successful: ${response.message()}")
            response.body()
        } else {
            Log.e("Repository", "Error: ${response.code()} ${response.message()}")
            null
        }
    }
}
