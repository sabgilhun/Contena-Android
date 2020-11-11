package com.sabgil.contena.presenter.home.adapter

import android.util.SparseArray
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
    private val sparseArray: SparseArray<Fragment> = SparseArray(2)

    init {
        sparseArray.append(0, Page.POST.fragment.newInstance())
        sparseArray.append(1, Page.NEW_PRODUCT.fragment.newInstance())
    }

    override fun getCount() = 2

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> sparseArray[0]
            1 -> sparseArray[1]
            else -> throw IllegalStateException("Exceed pager count")
        }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> Page.POST.title
            1 -> Page.NEW_PRODUCT.title
            else -> throw java.lang.IllegalStateException("Exceed pager count")
        }
    }

    fun getPage(position: Int) = sparseArray[position]

    private enum class Page(val title: String, val fragment: Class<out Fragment>) {
        POST("포스트", PostBookmarkFragment::class.java),
        NEW_PRODUCT("신상품", NewProductBookmarkFragment::class.java)
    }
}