package com.sabgil.contena.presenter.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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
import com.sabgil.contena.presenter.home.fragment.tabmanager.BaseTabFragment
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

    private var buttons: List<Pair<Tab, ButtonBinding>>? = emptyList()
    private var indexManager: IndexManager? = null

    fun setup(
        @IdRes
        fragmentContainerId: Int,
        fragmentManager: FragmentManager,
        onEmpty: () -> Unit,
        vararg tabData: Tab
    ) {
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
        indexManager = IndexManager(
            buttons.size,
            TabManager(
                fragmentContainerId,
                fragmentManager,
                tabData.map { it.tab },
                onEmpty
            ), this::tintTab
        )
    }

    fun goToBackTab() {
        indexManager?.popBackTab()
    }

    fun select(index: Int) {
        indexManager?.selectTab(index)
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
            text.text = button.text
            text.tintText(false, button)
            root.layoutParams = LinearLayout.LayoutParams(
                0, LayoutParams.WRAP_CONTENT, 1f
            )
            root.setOnClickListener { select(index) }
        }

    private fun tintTab(unselectedIdx: Int?, selectedIdx: Int) {
        buttons?.forEachIndexed { index, (button: Tab, binding: ButtonBinding) ->
            if (index == unselectedIdx) {
                binding.icon.tintIcon(false, button)
                binding.text.tintText(false, button)
            } else if (index == selectedIdx) {
                binding.icon.tintIcon(true, button)
                binding.text.tintText(true, button)
            }
        }
    }

    private fun ImageView.tintIcon(isSelected: Boolean, button: Tab) {
        setColorFilter(
            ContextCompat.getColor(
                context,
                if (isSelected) button.colorWhenSelected else button.colorWhenUnselected
            ), android.graphics.PorterDuff.Mode.SRC_IN
        )
    }

    private fun TextView.tintText(isSelected: Boolean, button: Tab) {
        setTextColor(
            ContextCompat.getColor(
                context,
                if (isSelected) button.colorWhenSelected else button.colorWhenUnselected
            )
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

    private class TabManager(
        @IdRes
        private val fragmentContainerId: Int,
        private val fragmentManager: FragmentManager,
        private val tabs: List<Class<out BaseTabFragment<*>>>,
        private val onEmpty: () -> Unit
    ) {

        fun initTab(index: Int) {
            fragmentManager.commit {
                add(
                    fragmentContainerId,
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
                if (newTab == null) {
                    add(
                        fragmentContainerId,
                        tabs[new].newInstance() as Fragment,
                        tabs[new].canonicalName
                    )
                } else {
                    show(newTab)
                }
            }
        }

        fun refreshTab(cur: Int) {
            fragmentManager.findFragmentByTag(tabs[cur].canonicalName)?.let {
                (it as BaseTabFragment<*>).refreshTab()
            }
        }

        fun onTabEmpty() {
            onEmpty()
        }

    }

    data class Tab(
        @DrawableRes
        val icon: Int,

        val text: String,

        @ColorRes
        val colorWhenSelected: Int,

        @ColorRes
        val colorWhenUnselected: Int,

        val tab: Class<out BaseTabFragment<*>>
    )
}