package com.example.shopinglist.domain

data class ShopItem(

    val name: String,
    val count: Int,
    val isActivated: Boolean,
    var id: Int = UNDEFINED_ID,
) {

    companion object {
        const val UNDEFINED_ID = -1
    }
}
