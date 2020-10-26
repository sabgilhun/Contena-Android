package com.sabgil.contena.presenter.home.adapter

import android.os.Handler
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.common.pagemanager.PageManageAdapter
import com.sabgil.contena.common.pagemanager.PageManagerViewHolder
import com.sabgil.contena.databinding.ItemPostBinding
import com.sabgil.contena.presenter.home.model.PostItem

class PostAdapter(
    private val navigator: Navigator,
    handler: Handler,
    loadMoreData: (Long) -> Unit
) : PageManageAdapter<PostItem>(
    handler,
    loadMoreData,
    PostViewHolderInitializerImpl()
) {

    override fun onBindItemViewHolder(
        item: PostItem,
        viewHolder: PageManagerViewHolder.ItemViewHolder
    ) {
        if (viewHolder is PostItemViewHolder) {
            with(viewHolder.binding) {
                shopLogoUrl = item.shopLogoUrl
                shopName = item.shopName
                subscriberCount = item.subscriberCount
                (itemViewPager.adapter as NewItemsViewPagerAdapter).replaceAll(item.newItemList)
            }
        }
    }

    override fun onCreateItemViewHolder(parent: ViewGroup): PageManagerViewHolder.ItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemPostBinding>(
            parent.context.layoutInflater,
            R.layout.item_post,
            parent,
            false
        )
        // TODO: dp 변환 작업 필요
        binding.itemViewPager.apply {
            clipToPadding = false
            pageMargin = 28
            setPadding(28, 0, 28, 0)
            adapter = NewItemsViewPagerAdapter()
        }
        binding.tabLayout.setupWithViewPager(binding.itemViewPager)
        return PostItemViewHolder(binding)
    }

    private class PostItemViewHolder(val binding: ItemPostBinding) :
        PageManagerViewHolder.ItemViewHolder(binding.root)

    interface Navigator {

        fun goToTotalProduction(postId: Long, shopName: String, uploadDate: String)

        fun goToOriginWeb()
    }
}
