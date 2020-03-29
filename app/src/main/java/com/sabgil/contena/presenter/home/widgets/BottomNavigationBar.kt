package com.sabgil.contena.presenter.home.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
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

    var tabManager: TabManager? = null
        set(value) {
            value?.initTab(selectedTabIndex)
            field = value
        }

    private lateinit var binding: WidgetBottomNavigationBarBinding

    private var selectedTabIndex: TabIndex = TabIndex.HOME

    private val tabClickConsumer: (TabIndex) -> Unit = { handleTabClick(selectedTabIndex, it) }

    fun goToBackTab() {
        tabManager?.let {
            val backTabIndex = it.getBackTab(selectedTabIndex)

            if (backTabIndex == null) {
                it.emptyTabBackStack()
            } else {
                changeTabColor(selectedTabIndex, backTabIndex)
                it.changeTab(selectedTabIndex, backTabIndex)
                it.emptyBackTab(selectedTabIndex)
                selectedTabIndex = backTabIndex
            }
        }
    }

    private fun initView() {
        binding = DataBindingUtil.inflate(
            context.layoutInflater,
            R.layout.widget_bottom_navigation_bar,
            this,
            true
        )

        initTabState()
        setupTabClickListener()
    }

    private fun setupTabClickListener() {
        binding.homeItem.setOnClickListener { tabClickConsumer(TabIndex.HOME) }
        binding.searchItem.setOnClickListener { tabClickConsumer(TabIndex.SEARCH) }
        binding.bookmarkItem.setOnClickListener { tabClickConsumer(TabIndex.BOOKMARK) }
        binding.settingsItem.setOnClickListener { tabClickConsumer(TabIndex.SETTINGS) }
    }

    private fun initTabState() {
        tintTab(selectedTabIndex, true)
    }

    private fun handleTabClick(oldTabIndex: TabIndex, newTabIndex: TabIndex) {
        changeTabColor(oldTabIndex, newTabIndex)

        tabManager?.let {
            if (oldTabIndex == newTabIndex) {
                it.refreshTab(newTabIndex)
            } else {
                it.changeTab(oldTabIndex, newTabIndex)
                it.setBackTab(newTabIndex, oldTabIndex)
            }
        }

        selectedTabIndex = newTabIndex
    }

    private fun changeTabColor(oldTabIndex: TabIndex, newTabIndex: TabIndex) {
        tintTab(oldTabIndex, false)
        tintTab(newTabIndex, true)
    }

    private fun tintTab(tabIndex: TabIndex, isSelected: Boolean) {
        when (tabIndex) {
            TabIndex.HOME -> tintIconAndText(
                icon = binding.homeIcon,
                text = binding.homeTitle,
                iconRes = tabIndex.mapToDrawableRes(isSelected),
                textColorRes = isSelected.mapToTextColor()
            )
            TabIndex.SEARCH -> tintIconAndText(
                icon = binding.searchIcon,
                text = binding.searchTitle,
                iconRes = tabIndex.mapToDrawableRes(isSelected),
                textColorRes = isSelected.mapToTextColor()
            )
            TabIndex.BOOKMARK -> tintIconAndText(
                icon = binding.bookmarkIcon,
                text = binding.bookmarkTitle,
                iconRes = tabIndex.mapToDrawableRes(isSelected),
                textColorRes = isSelected.mapToTextColor()
            )
            TabIndex.SETTINGS -> tintIconAndText(
                icon = binding.settingsIcon,
                text = binding.settingsTitle,
                iconRes = tabIndex.mapToDrawableRes(isSelected),
                textColorRes = isSelected.mapToTextColor()
            )
        }
    }

    private fun tintIconAndText(icon: ImageView, text: TextView, iconRes: Int, textColorRes: Int) {
        icon.setImageResource(iconRes)
        text.setTextColor(textColorRes)
    }

    private fun Boolean.mapToTextColor() =
        if (this) context.getColor(R.color.colorAmber600) else context.getColor(R.color.colorGray800)

    private fun TabIndex.mapToDrawableRes(isSelected: Boolean) =
        when (this) {
            TabIndex.HOME ->
                if (isSelected)
                    R.drawable.ic_home_amber400_24dp
                else
                    R.drawable.ic_home_gray800_24dp

            TabIndex.SEARCH ->
                if (isSelected)
                    R.drawable.ic_search_amber400_24dp
                else
                    R.drawable.ic_search_gray800_24dp
            TabIndex.BOOKMARK ->
                if (isSelected)
                    R.drawable.ic_bookmark_amber400_24dp
                else
                    R.drawable.ic_bookmark_gray800_24dp
            TabIndex.SETTINGS ->
                if (isSelected)
                    R.drawable.ic_settings_amber400_24dp
                else
                    R.drawable.ic_settings_gray800_24dp
        }

    enum class TabIndex {
        HOME, SEARCH, BOOKMARK, SETTINGS;
    }

    interface TabManager {

        fun initTab(initialTabIndex: TabIndex)

        fun changeTab(oldTabIndex: TabIndex, newTabIndex: TabIndex)

        fun refreshTab(tabIndex: TabIndex)

        fun emptyTabBackStack()

        fun setBackTab(targetIndex: TabIndex, backIndex: TabIndex)

        fun emptyBackTab(targetIndex: TabIndex)

        fun getBackTab(targetIndex: TabIndex): TabIndex?
    }
}