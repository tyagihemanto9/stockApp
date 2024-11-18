package com.example.stocktracker24

data class Meta(
    val version: String,
    val status: Int,
    val copywrite: String,
    val count: Int,
    val total: Int,
    val page: Int
)

data class StockData(
    val symbol: String,
    val symbolType: Int,
    val symbolName: String,
    val hasOptions: String,
    val lastPrice: String,
    val priceChange: String,
    val percentChange: String,
    val optionsImpliedVolatilityRank1y: String,
    val optionsTotalVolume: String,
    val optionsPutVolumePercent: String,
    val optionsCallVolumePercent: String,
    val optionsPutCallVolumeRatio: String,
    val tradeTime: String,
    val symbolCode: String
)

data class StockResponse(
    val meta: Meta,
    val body: List<StockData>
)