package com.example.shopinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shopinglist.R
import com.example.shopinglist.domain.shopItem.ShopItem

class ShopListAdapter :
    ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongCLickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLE -> R.layout.item_shop_enable
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disable
            else -> throw RuntimeException("Unknown viewType: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.isActivated) VIEW_TYPE_ENABLE else VIEW_TYPE_DISABLED
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.nameTextView.text = item.name
        holder.countTextView.text = item.count.toString()
        holder.view.setOnLongClickListener {
            onShopItemLongCLickListener?.invoke(item)
            true
        }
        holder.view.setOnClickListener { onShopItemClickListener?.invoke(item) }
    }

    companion object {
        const val VIEW_TYPE_ENABLE = 0
        const val VIEW_TYPE_DISABLED = 1
        const val MAX_POOL_SIZE = 15
    }
}