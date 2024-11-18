package com.example.stocktracker24

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.math.log

@SuppressLint("NullSafeMutableLiveData")
class StockViewModel : ViewModel() {

    private val _mostActiveStocks = MutableLiveData<StockResponse>()
    val mostActiveStocks: LiveData<StockResponse> get() = _mostActiveStocks

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        coroutineScope.launch {
            try {
                Log.d("ViewModel", "Fetching data...")
                val response = StockRepository().getMostActiveStocks(
                    "c7cdd43e26msh33d73b2e967f16fp1ecb84jsn77795e186e50",
                    "yahoo-finance15.p.rapidapi.com"
                )
                if (response != null) {
                    _mostActiveStocks.postValue(response)
                    Log.d("ViewModel", "Data received: ${response.body[0].symbolName}")
                } else {
                    Log.e("ViewModel", "Failed to retrieve data")
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Exception: ${e.message}")
            }
        }
    }
}
