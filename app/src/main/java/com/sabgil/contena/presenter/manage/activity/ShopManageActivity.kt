package com.sabgil.contena.presenter.manage.activity

import android.app.Activity
import android.os.Bundle
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.startWith
import com.sabgil.contena.databinding.ActivityShopManageBinding
import com.sabgil.contena.presenter.base.BaseActivity
import com.sabgil.contena.presenter.manage.adapter.SubscribedShopAdapter
import com.sabgil.contena.presenter.manage.viewmodel.ShopManageViewModel

class ShopManageActivity : BaseActivity<ActivityShopManageBinding>(R.layout.activity_shop_manage) {

    private lateinit var subscribedShopAdapter: SubscribedShopAdapter

    private val viewModel: ShopManageViewModel by lazy {
        getViewModel(
            ShopManageViewModel::class
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAppbar()
        setupSubscribedShopRecyclerView()

        viewModel.setupObserver()

        viewModel.loadSubscribedShopList()
    }

    private fun setupAppbar() {
        binding.backButton.setOnClickListener { finish() }
    }

    private fun setupSubscribedShopRecyclerView() {
        subscribedShopAdapter = SubscribedShopAdapter()
        binding.subscribedShopRecyclerView.adapter = subscribedShopAdapter
    }

    private fun ShopManageViewModel.setupObserver() {
        subscribedShopList.registerNonNullObserver(subscribedShopAdapter::replaceAll)
    }

    companion object {

        fun start(activity: Activity) {
            activity.startWith(ShopManageActivity::class)
        }
    }
}