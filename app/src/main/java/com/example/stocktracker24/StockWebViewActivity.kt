package com.example.stocktracker24

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class StockWebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_web_view)

        val webView: WebView = findViewById(R.id.stockWebView)
        val stockUrl = intent.getStringExtra("STOCK_URL")

        webView.webViewClient = WebViewClient()
        webView.settings.apply {
            javaScriptEnabled = true
            cacheMode = WebSettings.LOAD_DEFAULT
        }

        stockUrl?.let {
            webView.loadUrl(it)
        }
    }
}
