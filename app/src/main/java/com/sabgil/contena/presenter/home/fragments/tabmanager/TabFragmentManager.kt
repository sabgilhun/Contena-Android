package com.sabgil.contena.presenter.home.fragments.tabmanager

import android.util.SparseArray
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class TabFragmentManager(
    private val fragmentManager: FragmentManager,
    @IdRes
    private val fragmentContainer: Int,
    private val tabFragmentFactory: (Int) -> TabFragment
) {

    private var selectedTabIndex: Int? = null
    private var fragmentArray = SparseArray<TabFragment>()

    fun selectTab(index: Int) {
        val oldTab: TabFragment? = selectedTabIndex?.let(fragmentArray::get)
        val newTab: TabFragment = fragmentArray[index] ?: tabFragmentFactory(index)

        if (oldTab === newTab) {
            newTab.refreshTab()
            return
        }

        fragmentManager.beginTransaction()
            .ifNeededHideOldTab(oldTab)
            .addOrShowTab(fragmentArray[index] == null, newTab)
            .runOnCommit {
                fragmentArray.put(index, newTab)
                newTab.backStackTabIndex = selectedTabIndex
                selectedTabIndex = index
            }.commit()
    }

    fun goToBackTab(): Boolean = true

    private fun FragmentTransaction.ifNeededHideOldTab(
        tabFragment: TabFragment?
    ): FragmentTransaction =
        tabFragment?.let {
            this.hide(it as Fragment)
        } ?: this

    private fun FragmentTransaction.addOrShowTab(
        isNewTab: Boolean,
        tabFragment: TabFragment
    ): FragmentTransaction =
        if (isNewTab) {
            this.add(fragmentContainer, tabFragment)
        } else {
            this.show(tabFragment)
        }
}