package com.example.shopinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.shopinglist.R
import com.example.shopinglist.domain.shopItem.ShopItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShowItemActivity : AppCompatActivity() {

    private var shopItemId: Int = ShopItem.UNDEFINED_ID
    private lateinit var llInputName: TextInputLayout
    private lateinit var etInputName: TextInputEditText
    private lateinit var llInputCount: TextInputLayout
    private lateinit var etInputCount: TextInputEditText
    private lateinit var btnSave: Button
    private lateinit var viewModel: ShopItemViewModel
    private var screenMode = MODE_UNKNOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews()
        launchRightMode()
        addTextChangedListener()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(this) {
            val message = if (it) getString(R.string.error_input_name) else null
            llInputName.error = message
        }

        viewModel.errorInputCount.observe(this) {
            val message = if (it) getString(R.string.error_input_count) else null
            llInputCount.error = message
        }
    }

    private fun addTextChangedListener() {
        etInputName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        etInputCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this) {
            etInputName.setText(it.name)
            etInputCount.setText(it.count.toString())
        }
        btnSave.setOnClickListener {
            viewModel.updateShopItem(
                etInputName.text?.toString(),
                etInputCount.text?.toString()
            )

            viewModel.shouldCloseScreen.observe(this) {
                finish()
            }
        }
    }

    private fun launchAddMode() {
        btnSave.setOnClickListener {
            viewModel.insertShopItem(
                etInputName.text?.toString(),
                etInputCount.text?.toString()
            )
            viewModel.shouldCloseScreen.observe(this) {
                finish()
            }
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

    private fun initViews() {
        llInputName = findViewById(R.id.ll_input_name)
        etInputName = findViewById(R.id.et_input_name)
        llInputCount = findViewById(R.id.ll_input_count)
        etInputCount = findViewById(R.id.et_input_count)
        btnSave = findViewById(R.id.btn_save)
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mod"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShowItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, idItem: Int): Intent {
            val intent = Intent(context, ShowItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, idItem)
            return intent
        }
    }
}