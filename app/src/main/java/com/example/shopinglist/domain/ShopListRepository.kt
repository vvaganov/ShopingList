package com.example.shopinglist.domain

interface ShopListRepository {

    fun insertShopItem(item: ShopItem)

    fun getShopList(): List<ShopItem>

    fun updateShopItem(idItem: Int)

    fun getShopItemById(idItem: Int): ShopItem

    fun deleteShopItem(idItem: Int)
}