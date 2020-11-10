package com.sabgil.contena.presenter.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.databinding.WidgetBottomNavBarBinding
import com.sabgil.contena.databinding.WidgetBottomNavBarButtonBinding
import com.sabgil.contena.presenter.home.fragment.BaseTabFragment

class BottomNavigationBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: WidgetBottomNavBarBinding =
        DataBindingUtil.inflate(
            context.layoutInflater,
            R.layout.widget_bottom_nav_bar,
            this,
            true
        )

    @IdRes
    private var tabContainerId: Int? = null
    private var fm: FragmentManager? = null

    private var buttons: List<Pair<Tab, ImageView>>? = emptyList()
    private var tabManager: TabManager2? = null

    private var onEmpty: (() -> Unit)? = null
    private var onChangeTab: ((Tab) -> Unit)? = null

    fun init(@IdRes tabContainerId: Int, fragmentManager: FragmentManager) {
        this.tabContainerId = tabContainerId
        this.fm = fragmentManager
    }

    fun tabSetup(vararg tabData: Tab) {
        val containerId = requireNotNull(tabContainerId)
        val fm = requireNotNull(fm)

        if (buttons?.isNotEmpty() == true) {
            binding.bottomNavButtonContainer.removeAllViews()
        }

        val buttons: List<Pair<Tab, ImageView>> =
            tabData.asList().map { button ->
                val buttonBinding = inflateButtonBinding(button)
                binding.bottomNavButtonContainer.addView(buttonBinding.root)
                button to buttonBinding.icon
            }

        this.buttons = buttons

        tabManager = TabManager2(containerId, fm, buttons.map { it.first })
    }

    fun goToBackTab() {
        tabManager?.goToBackTab()
    }

    fun select(tab: Tab) {
        tabManager?.selectTab(tab)
    }

    fun currentTabRefresh() {
        tabManager?.currentTabRefresh()
    }

    fun setOnEmpty(onEmpty: () -> Unit) {
        this.onEmpty = onEmpty
    }

    fun setOnChangeTab(onChangeTab: (Tab) -> Unit) {
        this.onChangeTab = onChangeTab
    }

    private fun inflateButtonBinding(button: Tab) =
        DataBindingUtil.inflate<WidgetBottomNavBarButtonBinding>(
            context.layoutInflater,
            R.layout.widget_bottom_nav_bar_button,
            this,
            false
        ).apply {
            icon.setImageResource(button.icon)
            icon.tintIcon(false, button)
            root.layoutParams = LinearLayout.LayoutParams(
                0, LayoutParams.WRAP_CONTENT, 1f
            )
            root.setOnClickListener { select(button) }
        }

    private fun tintTab(unselectedIdx: Int?, selectedIdx: Int) {
        buttons?.forEachIndexed { index, (button: Tab, icon: ImageView) ->
            if (index == unselectedIdx) {
                icon.tintIcon(false, button)
            } else if (index == selectedIdx) {
                icon.tintIcon(true, button)
            }
        }
    }

    private fun ImageView.tintIcon(isSelected: Boolean, button: Tab) {
        setColorFilter(
            ContextCompat.getColor(
                context,
                if (isSelected) button.selectedColor else button.unselectedColor
            ), android.graphics.PorterDuff.Mode.SRC_IN
        )
    }

    private inner class TabManager2(
        @IdRes
        private val containerId: Int,
        private val fragmentManager: FragmentManager,
        private val tabs: List<Tab>
    ) {
        private val backStack: BackStack

        init {
            val initTab = tabs.first()
            backStack = BackStack(tabs.size, initTab)
            fragmentManager.commit { addTab(initTab) }
            tintTab(null, 0)
        }

        fun selectTab(new: Tab) {
            val cur = backStack.currentTab()
            if (cur == new) {
                scrollOnTop(new)
                return
            } else {
                changeTab(cur, new)
                backStack.push(new)
                onChangeTab?.invoke(new)
            }
        }

        fun goToBackTab() {
            if (backStack.peek() == null) {
                onEmpty?.invoke()
            } else {
                changeTab(backStack.currentTab(), backStack.pop())
            }
        }

        fun currentTabRefresh() {
            findTabFragment(backStack.currentTab())?.let {
                (it as BaseTabFragment<*>).refreshTab()
            }
        }

        private fun scrollOnTop(tab: Tab) {
            findTabFragment(tab)?.let {
                (it as BaseTabFragment<*>).scrollOnTop()
            }
        }

        private fun changeTab(old: Tab, new: Tab) {
            val oldTabFragment = findTabFragment(old)
            val newTabFragment = findTabFragment(new)
            fragmentManager.commit {
                oldTabFragment?.let { hide(it) }
                if (newTabFragment == null) {
                    addTab(new)
                } else {
                    show(newTabFragment)
                }
            }

            val oldIndex = tabs.indexOf(old)
            val newIndex = tabs.indexOf(new)
            tintTab(oldIndex, newIndex)
        }

        private fun findTabFragment(tab: Tab): Fragment? =
            fragmentManager.findFragmentByTag(tab.getTag())

        private fun FragmentTransaction.addTab(tab: Tab) {
            add(
                containerId,
                tab.fragmentClazz.newInstance(),
                tab.getTag()
            )
        }
    }

    private class BackStack(private val maxSize: Int, initTab: Tab) {
        private val backStack: MutableSet<Tab> = LinkedHashSet()
        private var currentTab: Tab = initTab

        fun currentTab() = currentTab

        fun push(tab: Tab) {
            if (backStack.size < maxSize) {
                backStack.add(currentTab)
                currentTab = tab
            }
        }

        fun pop(): Tab {
            require(backStack.isNotEmpty())
            return backStack.last().let {
                backStack.remove(it)
                currentTab = it
                it
            }
        }

        fun peek(): Tab? = if (backStack.isNotEmpty()) backStack.last() else null
    }

    interface Tab {
        @get:DrawableRes
        val icon: Int

        @get:ColorRes
        val selectedColor: Int

        @get:ColorRes
        val unselectedColor: Int

        val fragmentClazz: Class<out BaseTabFragment<*>>

        fun getTag() = fragmentClazz.canonicalName
    }
}