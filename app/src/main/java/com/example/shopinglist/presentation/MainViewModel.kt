package com.example.shopinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopinglist.data.ShopLIstRepositoryImpl
import com.example.shopinglist.domain.DeleteShopItemUseCase
import com.example.shopinglist.domain.GetShopListUseCase
import com.example.shopinglist.domain.ShopItem
import com.example.shopinglist.domain.UpdateShopItemUseCase

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