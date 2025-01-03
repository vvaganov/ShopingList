package com.example.shopinglist.domain

class UpdateShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun updateShopItem(idItem: Int) {
        shopListRepository.updateShopItem(idItem)
    }
}