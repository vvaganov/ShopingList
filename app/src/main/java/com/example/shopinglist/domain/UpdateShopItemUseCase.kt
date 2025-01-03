package com.example.shopinglist.domain

class UpdateShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun updateShopItem(item: ShopItem) {
        shopListRepository.updateShopItem(item)
    }
}