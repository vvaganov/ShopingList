package com.example.shopinglist.domain

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun deleteShopItem(idItem: Int) {
        shopListRepository.deleteShopItem(idItem)
    }
}