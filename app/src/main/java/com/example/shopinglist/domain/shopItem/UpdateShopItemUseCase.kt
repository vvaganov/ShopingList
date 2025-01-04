package com.example.shopinglist.domain.shopItem

class UpdateShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun updateShopItem(item: ShopItem) {
        shopListRepository.updateShopItem(item)
    }
}