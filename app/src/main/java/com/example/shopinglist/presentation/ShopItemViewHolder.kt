package com.example.shopinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinglist.R

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val countTextView: TextView = view.findViewById(R.id.tv_count)
    val nameTextView: TextView = view.findViewById(R.id.tv_name)
}