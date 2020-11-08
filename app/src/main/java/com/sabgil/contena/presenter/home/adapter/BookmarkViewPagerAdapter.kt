package com.sabgil.contena.presenter.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sabgil.contena.presenter.home.fragment.NewProductBookmarkFragment
import com.sabgil.contena.presenter.home.fragment.PostBookmarkFragment

class BookmarkViewPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(
    fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    override fun getCount() = 2

    override fun getItem(position: Int) =
        when (position) {
            0 -> Tab.POST.fragment.newInstance()
            1 -> Tab.NEW_PRODUCT.fragment.newInstance()
            else -> throw IllegalStateException("Exceed pager count")
        }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> Tab.POST.title
            1 -> Tab.NEW_PRODUCT.title
            else -> throw java.lang.IllegalStateException("Exceed pager count")
        }
    }

    private enum class Tab(val title: String, val fragment: Class<out Fragment>) {
        POST("포스트", PostBookmarkFragment::class.java),
        NEW_PRODUCT("신상품", NewProductBookmarkFragment::class.java)
    }
}