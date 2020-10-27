package com.sabgil.contena.presenter.home.fragment

import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.common.adapter.*
import com.sabgil.contena.databinding.*
import com.sabgil.contena.presenter.home.adapter.NewItemsViewPagerAdapter
import com.sabgil.contena.presenter.home.fragment.tabmanager.BaseTabFragment
import com.sabgil.contena.presenter.home.model.EmptyItem
import com.sabgil.contena.presenter.home.model.LoadingItem
import com.sabgil.contena.presenter.home.model.NoMoreItem
import com.sabgil.contena.presenter.home.model.PostItem
import com.sabgil.contena.presenter.home.viewmodel.NewItemTabViewModel
import com.sabgil.contena.presenter.settings.activity.SettingsActivity

class NewItemTabFragment :
    BaseTabFragment<FragmentNewItemTabBinding>(R.layout.fragment_new_item_tab) {

    private val viewModel: NewItemTabViewModel by lazy {
        getViewModel(NewItemTabViewModel::class)
    }

    private lateinit var postAdapter: MultiViewTypeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAppbar()
        setupPostRecyclerView()

        viewModel.setupObserver()
        viewModel.loadPostList()
    }

    private fun setupAppbar() {
        binding.settingImageButton.setOnClickListener {
            SettingsActivity.start(requireActivity())
        }
    }

    private fun setupPostRecyclerView() {
        val multiViewTypesSetup = multiViewType {
            definePostItem()
            defineLoadingItem()
            defineEmptyItem()
            defineNoMoreItem()
        }
        postAdapter = MultiViewTypeAdapter(multiViewTypesSetup)
        binding.postRecyclerView.adapter = postAdapter
    }

    private fun NewItemTabViewModel.setupObserver() {
        postList.registerObserver(postAdapter::replaceAll)
    }

    override fun refreshTab() {
        // TODO: scroll top
    }

    private fun ViewTypesSetup.definePostItem() =
        viewType<PostItem, ItemPostBinding>(R.layout.item_post) {
            onCreate { binding, _ ->
                // TODO: dp 변환 작업 필요
                binding.itemViewPager.apply {
                    clipToPadding = false
                    pageMargin = 28
                    setPadding(28, 0, 28, 0)
                    adapter = NewItemsViewPagerAdapter()
                }
                binding.tabLayout.setupWithViewPager(binding.itemViewPager)
            }

            onBind { item, binding, _ ->
                with(binding) {
                    shopLogoUrl = item.shopLogoUrl
                    shopName = item.shopName
                    subscriberCount = item.subscriberCount
                    (itemViewPager.adapter as NewItemsViewPagerAdapter).replaceAll(item.newItemList)
                }
            }
        }

    private fun ViewTypesSetup.defineLoadingItem() =
        viewType<LoadingItem, ItemPostLoadingBinding>(R.layout.item_post_loading) {
            onBind { loadingItem, _, _ ->
                if (!loadingItem.statedLoading.getAndSet(true)) {
                    viewModel.loadPostList(loadingItem.nextCursor)
                }
            }
        }

    private fun ViewTypesSetup.defineEmptyItem() =
        viewType<EmptyItem, ItemPostEmptyBinding>(R.layout.item_post_empty)

    private fun ViewTypesSetup.defineNoMoreItem() =
        viewType<NoMoreItem, ItemPostNoMoreBinding>(R.layout.item_post_no_more)

    companion object {
        fun newInstance() = NewItemTabFragment()
    }
}