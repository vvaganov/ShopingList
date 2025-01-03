package com.example.shopinglist.domain

class InsertShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun insertShopItem(item: ShopItem) {
        shopListRepository.insertShopItem(item)
    }
}