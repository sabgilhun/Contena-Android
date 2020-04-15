package com.sabgil.contena.presenter.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.databinding.WidgetBottomNavigationBarBinding

class BottomNavigationBar : FrameLayout {

    var tabManager: TabManager? = null
        set(value) {
            value?.initTab(selectedTabIndex)
            field = value
        }

    private lateinit var binding: WidgetBottomNavigationBarBinding

    private var selectedTabIndex: TabIndex =
        TabIndex.NEW_ITEM

    private val tabClickConsumer: (TabIndex) -> Unit = { handleTabClick(selectedTabIndex, it) }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

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
        binding.homeItem.setOnClickListener { tabClickConsumer(TabIndex.NEW_ITEM) }
        binding.searchItem.setOnClickListener { tabClickConsumer(TabIndex.SEARCH) }
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
            TabIndex.NEW_ITEM -> tintIconAndText(
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
        }
    }

    private fun tintIconAndText(icon: ImageView, text: TextView, iconRes: Int, textColorRes: Int) {
        icon.setImageResource(iconRes)
        text.setTextColor(textColorRes)
    }

    private fun Boolean.mapToTextColor() =
        if (this) context.getColor(R.color.colorGray900) else context.getColor(R.color.colorGray400)

    private fun TabIndex.mapToDrawableRes(isSelected: Boolean) =
        when (this) {
            TabIndex.NEW_ITEM ->
                if (isSelected)
                    R.drawable.vector_list_gray900
                else
                    R.drawable.vector_list_gray400

            TabIndex.SEARCH ->
                if (isSelected)
                    R.drawable.vector_search_gray900
                else
                    R.drawable.vector_search_gray400
        }

    enum class TabIndex {
        NEW_ITEM, SEARCH;
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