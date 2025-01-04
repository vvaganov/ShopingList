package com.example.shopinglist.presentation

import androidx.lifecycle.ViewModel
import com.example.shopinglist.data.ShopLIstRepositoryImpl
import com.example.shopinglist.domain.shopItem.DeleteShopItemUseCase
import com.example.shopinglist.domain.shopItem.GetShopListUseCase
import com.example.shopinglist.domain.shopItem.ShopItem
import com.example.shopinglist.domain.shopItem.UpdateShopItemUseCase

class MainViewModel : ViewModel() {

    private val shopListRepository = ShopLIstRepositoryImpl

    private val getShopItemList = GetShopListUseCase(shopListRepository)
    private val deleteShopItem = DeleteShopItemUseCase(shopListRepository)
    private val updateShopItem = UpdateShopItemUseCase(shopListRepository)

    val itemShopList = getShopItemList.getShopList()

      fun deleteItem(item: ShopItem) {
        deleteShopItem.deleteShopItem(item)
    }

    fun changeActivatedItemState(item: ShopItem) {
        updateShopItem.updateShopItem(item.copy(isActivated = !item.isActivated))
    }
}