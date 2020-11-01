package com.sabgil.contena.presenter.home.activity

import android.content.Context
import android.os.Bundle
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.startOnTop
import com.sabgil.contena.databinding.ActivityHomeBinding
import com.sabgil.contena.presenter.base.BaseActivity
import com.sabgil.contena.presenter.home.fragment.NewItemTabFragment
import com.sabgil.contena.presenter.home.fragment.SearchTabFragment
import com.sabgil.contena.presenter.home.viewmodel.HomeViewModel
import com.sabgil.contena.presenter.widget.BottomNavigationBar

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val viewModel: HomeViewModel by lazy { getViewModel(HomeViewModel::class) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    override fun onBackPressed() = binding.bottomNavigation.goToBackTab()

    private fun setupViews() {
        with(binding.bottomNavigation) {
            init(R.id.tabContainer, supportFragmentManager)
            tabSetup(
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

            setOnEmpty { finish() }
            setOnChangeTab {
                if (it is NewItemTabFragment && viewModel.needsPostReload.value == true) {
                    it.loadFirstPage()
                }
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startOnTop(HomeActivity::class)
        }
    }
}
