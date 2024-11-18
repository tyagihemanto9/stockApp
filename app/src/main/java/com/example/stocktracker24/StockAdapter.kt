package com.example.stocktracker24

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StockAdapter(private var stocks: List<StockData>) : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {
    private var filteredStocks = stocks

    class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val symbolName: TextView = itemView.findViewById(R.id.tvSymbolName)
        val symbol: TextView = itemView.findViewById(R.id.tvSymbol)
        val priceChange: TextView = itemView.findViewById(R.id.tvPriceChange)
        val percentChange: TextView = itemView.findViewById(R.id.tvPercentChange)
        val lastPrice: TextView = itemView.findViewById(R.id.tvLastPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = filteredStocks[position]
        holder.symbolName.text = stock.symbolName
        holder.symbol.text = stock.symbol
        holder.priceChange.text = "Price Change: ${stock.priceChange}"
        holder.percentChange.text = "Percent Change: ${stock.percentChange}"
        holder.lastPrice.text = "Last Price: ${stock.lastPrice}"

        // Construct the Yahoo Finance URL based on the stock symbol
        val stockUrl = "https://finance.yahoo.com/quote/${stock.symbol}"

        // Set an OnClickListener to open the stock's page
//        holder.itemView.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(stockUrl))
//            holder.itemView.context.startActivity(intent)
//        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, StockWebViewActivity::class.java)
            intent.putExtra("STOCK_URL", stockUrl)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return filteredStocks.size
    }

    fun filter(query: String) {
        filteredStocks = if (query.isEmpty()) {
            stocks // If the query is empty, show all stocks
        } else {
            stocks.filter {
                it.symbolName.contains(query, ignoreCase = true) ||
                        it.symbol.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}
