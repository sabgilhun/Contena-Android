package com.sabgil.contena.presenter.home.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.databinding.DataBindingUtil
import com.sabgil.contena.R
import com.sabgil.contena.commons.ext.layoutInflater
import com.sabgil.contena.databinding.WidgetBottomNavigationBarBinding

class BottomNavigationBar : FrameLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    private lateinit var binding: WidgetBottomNavigationBarBinding
    var tabClickConsumer: ((Int) -> (Unit))? = null

    private var selectedTab: Tab = Tab.HOME
        set(value) {
            tabClickConsumer?.invoke(value.index)
            if (field != value) {
                changeTabColor(field, value)
                field = value
            }
        }

    private fun initView() {
        binding = DataBindingUtil.inflate(
            context.layoutInflater,
            R.layout.widget_bottom_navigation_bar,
            this,
            true
        )

        binding.homeItem.setOnClickListener { selectedTab = Tab.HOME }
        binding.searchItem.setOnClickListener { selectedTab = Tab.SEARCH }
        binding.bookmarkItem.setOnClickListener { selectedTab = Tab.BOOKMARK }
        binding.settingsItem.setOnClickListener { selectedTab = Tab.SETTINGS }

        changeTabColor(null, Tab.HOME)
    }

    private fun tintTabItem(tab: Tab, isSelected: Boolean) {
        val colorRes = if (isSelected) SELECTED_ITEM_COLOR else NOT_SELECTED_ITEM_COLOR

        when (tab) {
            Tab.HOME -> paintColorToItem(binding.homeIcon, binding.homeTitle, colorRes)
            Tab.SEARCH -> paintColorToItem(binding.searchIcon, binding.searchTitle, colorRes)
            Tab.BOOKMARK -> paintColorToItem(binding.bookmarkIcon, binding.bookmarkTitle, colorRes)
            Tab.SETTINGS -> paintColorToItem(binding.settingsIcon, binding.settingsTitle, colorRes)
        }
    }

    private fun changeTabColor(oldTab: Tab?, newTab: Tab) {
        oldTab?.let {
            tintTabItem(it, false)
        }

        tintTabItem(newTab, true)
    }

    private fun paintColorToItem(icon: ImageView, text: TextView, @ColorRes colorRes: Int) {
        val color = context.getColor(colorRes)
        icon.background.setTint(color)
        text.setTextColor(color)
    }

    private enum class Tab(val index: Int) {
        HOME(1), SEARCH(2), BOOKMARK(3), SETTINGS(4)
    }

    companion object {
        @ColorRes
        private const val SELECTED_ITEM_COLOR = R.color.colorAmber600
        @ColorRes
        private const val NOT_SELECTED_ITEM_COLOR = R.color.colorGray800
    }
}