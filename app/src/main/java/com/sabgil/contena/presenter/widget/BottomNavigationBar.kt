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
import androidx.fragment.app.commit
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.presenter.home.fragment.BaseTabFragment
import com.sabgil.contena.databinding.WidgetBottomNavBarBinding as LayoutBinding
import com.sabgil.contena.databinding.WidgetBottomNavBarButtonBinding as ButtonBinding

class BottomNavigationBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: LayoutBinding =
        DataBindingUtil.inflate(
            context.layoutInflater,
            R.layout.widget_bottom_nav_bar,
            this,
            true
        )

    @IdRes
    private var tabContainerId: Int? = null
    private var fragmentManager: FragmentManager? = null

    private var buttons: List<Pair<Tab, ButtonBinding>>? = emptyList()
    private var indexManager: IndexManager? = null

    private var onEmpty: (() -> Unit)? = null
    private var onChangeTab: ((BaseTabFragment<*>) -> Unit)? = null

    fun init(@IdRes tabContainerId: Int, fragmentManager: FragmentManager) {
        this.tabContainerId = tabContainerId
        this.fragmentManager = fragmentManager
    }

    fun tabSetup(vararg tabData: Tab) {
        val containerId = requireNotNull(tabContainerId)
        val fm = requireNotNull(fragmentManager)

        if (buttons?.isNotEmpty() == true) {
            binding.bottomNavButtonContainer.removeAllViews()
        }

        val buttons: List<Pair<Tab, ButtonBinding>> =
            tabData.asList().mapIndexed { idx, button ->
                val buttonBinding = inflateButtonBinding(idx, button)
                binding.bottomNavButtonContainer.addView(buttonBinding.root)
                button to buttonBinding
            }

        this.buttons = buttons

        val tabManager = TabManager(containerId, fm, tabData.map { it.tab })
        indexManager = IndexManager(
            stackSize = buttons.size, tabManager = tabManager, onChangeTab = this::tintTab
        )
    }

    fun goToBackTab() {
        indexManager?.popBackTab()
    }

    fun select(index: Int) {
        indexManager?.selectTab(index)
    }

    fun setOnEmpty(onEmpty: () -> Unit) {
        this.onEmpty = onEmpty
    }

    fun setOnChangeTab(onChangeTab: (BaseTabFragment<*>) -> Unit) {
        this.onChangeTab = onChangeTab
    }

    private fun inflateButtonBinding(index: Int, button: Tab) =
        DataBindingUtil.inflate<ButtonBinding>(
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
            root.setOnClickListener { select(index) }
        }

    private fun tintTab(unselectedIdx: Int?, selectedIdx: Int) {
        buttons?.forEachIndexed { index, (button: Tab, binding: ButtonBinding) ->
            if (index == unselectedIdx) {
                binding.icon.tintIcon(false, button)
            } else if (index == selectedIdx) {
                binding.icon.tintIcon(true, button)
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

    private class IndexManager(
        private val stackSize: Int,
        private val tabManager: TabManager,
        private val onChangeTab: (Int?, Int) -> Unit
    ) {
        private val backStack: MutableSet<Int> = LinkedHashSet()
        var index: Int

        init {
            index = 0
            tabManager.initTab(index)
            onChangeTab(null, 0)
        }

        fun popBackTab() {
            val cur = index
            val new = peek()

            if (new == null) {
                tabManager.onTabEmpty()
            } else {
                tabManager.changeTab(cur, new)
                onChangeTab(cur, new)
                pop()
                index = new
            }
        }

        fun selectTab(new: Int) {
            val cur = index

            if (cur == new) {
                tabManager.refreshTab(new)
            } else {
                tabManager.changeTab(cur, new)
                onChangeTab(cur, new)
                push(cur)
                index = new
            }
        }

        private fun push(index: Int) {
            if (backStack.size < stackSize) {
                backStack.add(index)
            }
        }

        private fun pop(): Int? =
            if (backStack.isNotEmpty()) {
                val item = backStack.last()
                backStack.remove(backStack.size - 1)
                item
            } else null

        private fun peek(): Int? =
            if (backStack.isNotEmpty()) backStack.last() else null
    }

    private inner class TabManager(
        @IdRes
        private val containerId: Int,
        private val fragmentManager: FragmentManager,
        private val tabs: List<Class<out BaseTabFragment<*>>>
    ) {

        fun initTab(index: Int) {
            fragmentManager.commit {
                add(
                    containerId,
                    tabs[index].newInstance() as Fragment,
                    tabs[index].canonicalName
                )
            }
        }

        fun changeTab(cur: Int, new: Int) {
            val curTab = fragmentManager.findFragmentByTag(tabs[cur].canonicalName)
            val newTab = fragmentManager.findFragmentByTag(tabs[new].canonicalName)
            fragmentManager.commit {
                curTab?.let { hide(it) }
                val addedTab = if (newTab == null) {
                    val newInstanceTab = tabs[new].newInstance()
                    add(
                        containerId,
                        newInstanceTab,
                        tabs[new].canonicalName
                    )
                    newInstanceTab
                } else {
                    show(newTab)
                    newTab as BaseTabFragment<*>
                }
                this@BottomNavigationBar.onChangeTab?.invoke(addedTab)
            }
        }

        fun refreshTab(cur: Int) {
            fragmentManager.findFragmentByTag(tabs[cur].canonicalName)?.let {
                (it as BaseTabFragment<*>).refreshTab()
            }
        }

        fun onTabEmpty() {
            this@BottomNavigationBar.onEmpty?.invoke()
        }
    }

    data class Tab(
        @DrawableRes
        val icon: Int,

        @ColorRes
        val selectedColor: Int,

        @ColorRes
        val unselectedColor: Int,

        val tab: Class<out BaseTabFragment<*>>
    )
}