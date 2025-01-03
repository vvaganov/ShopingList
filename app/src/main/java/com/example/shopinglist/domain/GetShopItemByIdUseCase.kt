package com.example.shopinglist.domain

class GetShopItemByIdUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItemById(idItem: Int): ShopItem {
        return shopListRepository.getShopItemById(idItem)
    }
}