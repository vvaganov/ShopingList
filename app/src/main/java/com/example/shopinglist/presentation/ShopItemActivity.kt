package com.example.shopinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.shopinglist.R
import com.example.shopinglist.domain.shopItem.ShopItem
import com.example.shopinglist.presentation.ShopItemFragment.Companion.EXTRA_SCREEN_MODE
import com.example.shopinglist.presentation.ShopItemFragment.Companion.EXTRA_SHOP_ITEM_ID
import com.example.shopinglist.presentation.ShopItemFragment.Companion.MODE_ADD
import com.example.shopinglist.presentation.ShopItemFragment.Companion.MODE_EDIT
import com.example.shopinglist.presentation.ShopItemFragment.Companion.MODE_UNKNOWN
import com.example.shopinglist.presentation.ShopItemFragment.Companion.newIntentAddItem
import com.example.shopinglist.presentation.ShopItemFragment.Companion.newInstanceEditItem

class ShopItemActivity : AppCompatActivity() {

    private var shopItemId: Int = ShopItem.UNDEFINED_ID
    private var screenMode = MODE_UNKNOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE))
            throw RuntimeException("Param screen mode is absent ")
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD)
            throw RuntimeException("Unknown screen mode $mode")
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID))
                throw RuntimeException("Param shop_item_id is absent")
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_ADD -> newIntentAddItem()
            MODE_EDIT -> newInstanceEditItem(shopItemId)
            else -> throw RuntimeException("Unknown screenMode $screenMode")
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }

    companion object {

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, idItem: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, idItem)
            return intent
        }
    }
}