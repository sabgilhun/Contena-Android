package com.sabgil.contena.presenter.home.activity

import android.content.Context
import android.os.Bundle
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.startOnTop
import com.sabgil.contena.databinding.ActivityHomeBinding
import com.sabgil.contena.presenter.base.BaseActivity
import com.sabgil.contena.presenter.home.fragment.NewItemTabFragment
import com.sabgil.contena.presenter.home.fragment.SearchTabFragment
import com.sabgil.contena.presenter.widget.BottomNavigationBar

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bottomNavigation.setup()
    }

    override fun onBackPressed() = binding.bottomNavigation.goToBackTab()

    private fun BottomNavigationBar.setup() {
        setup(
            R.id.tabContainer,
            supportFragmentManager,
            this@HomeActivity::finish,

            BottomNavigationBar.Tab(
                icon = R.drawable.vector_list_gray900,
                text = "신상품",
                colorWhenSelected = R.color.colorBeigeWhite,
                colorWhenUnselected = R.color.colorDarkGray,
                tab = NewItemTabFragment::class.java
            ),

            BottomNavigationBar.Tab(
                icon = R.drawable.vector_search_begie_white,
                text = "쇼핑몰 검색",
                colorWhenSelected = R.color.colorBeigeWhite,
                colorWhenUnselected = R.color.colorDarkGray,
                tab = SearchTabFragment::class.java
            )
        )
        requestLayout()
    }

    companion object {
        fun start(context: Context) {
            context.startOnTop(HomeActivity::class)
        }
    }
}
