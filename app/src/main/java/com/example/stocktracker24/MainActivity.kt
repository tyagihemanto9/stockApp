package com.example.stocktracker24

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var stockViewModel: StockViewModel
    private lateinit var stockAdapter: StockAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editTextText)
        stockViewModel = ViewModelProvider(this).get(StockViewModel::class.java)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        stockViewModel.mostActiveStocks.observe(this) { stockResponse ->
            stockAdapter = StockAdapter(stockResponse.body)
            recyclerView.adapter = stockAdapter
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString()
                stockAdapter.filter(searchText)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}