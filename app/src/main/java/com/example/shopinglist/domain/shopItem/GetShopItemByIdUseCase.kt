package com.example.shopinglist.domain.shopItem

class GetShopItemByIdUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItemById(idItem: Int): ShopItem {
        return shopListRepository.getShopItemById(idItem)
    }
}