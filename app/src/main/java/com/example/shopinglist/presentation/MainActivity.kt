package com.example.shopinglist.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shopItemContainer = findViewById(R.id.shop_item_container)
        setupRecyclerView()
        setupAddItemClick()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.itemShopList.observe(this) { list ->
            shopListAdapter.submitList(list)
        }
    }

    private fun launchFragment(fragment: ShopItemFragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.shop_item_container,
                fragment
            )
            .addToBackStack("fragment")
            .commit()
    }

    private fun setupAddItemClick(){
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddItem.setOnClickListener {
            if (isOnePaneMode()){
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            }else{
               launchFragment(ShopItemFragment.newIntentAddItem())
            }
        }
    }

    private fun isOnePaneMode(): Boolean{
        return shopItemContainer == null
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        shopListAdapter = ShopListAdapter()
        with(rvShopList) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLE,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        itemLongClick()
        itemClick()
        itemDeleteSwipe(rvShopList)
    }

    private fun itemDeleteSwipe(rvShopList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun itemClick() {
        shopListAdapter.onShopItemClickListener = { shopItem ->
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditItem(this, shopItem.id)
                startActivity(intent)
            } else {
              launchFragment(ShopItemFragment.newInstanceEditItem(shopItem.id))
            }
        }
    }

    private fun itemLongClick() {
        shopListAdapter.onShopItemLongCLickListener = { shopItem ->
            viewModel.changeActivatedItemState(shopItem)
        }
    }
}