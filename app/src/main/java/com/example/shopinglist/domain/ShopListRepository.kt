package com.example.shopinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun insertShopItem(item: ShopItem)

    fun getShopList(): LiveData<List<ShopItem>>

    fun updateShopItem(item: ShopItem)

    fun getShopItemById(idItem: Int): ShopItem

    fun deleteShopItem(item: ShopItem)
}