package com.sabgil.contena.presenter.home.adapter

import android.os.Handler
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.common.pagemanager.PageManageAdapter
import com.sabgil.contena.common.pagemanager.PageManagerViewHolder
import com.sabgil.contena.databinding.ItemPostBinding
import com.sabgil.contena.presenter.home.model.PostListItem

class PostAdapter(
    private val navigator: Navigator,
    handler: Handler,
    loadMoreData: (Long) -> Unit
) : PageManageAdapter<PostListItem>(
    handler,
    loadMoreData,
    PostViewHolderInitializerImpl()
) {

    override fun onBindItemViewHolder(
        item: PostListItem,
        viewHolder: PageManagerViewHolder.ItemViewHolder
    ) {
        if (viewHolder is PostItemViewHolder) {
            viewHolder.apply {
                binding.postListItem = item
                binding.newProductsSummaryView.imageUrlList = item.newItemList.map { it.imageUrl }
                binding.newProductsSummaryView.setOnClickListener {
                    navigator.goToTotalProduction(item.postId, item.shopName, item.uploadDate)
                }
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
        return PostItemViewHolder(binding)
    }

    private class PostItemViewHolder(val binding: ItemPostBinding) :
        PageManagerViewHolder.ItemViewHolder(binding.root)

    interface Navigator {

        fun goToTotalProduction(postId: Long, shopName: String, uploadDate: String)

        fun goToOriginWeb()
    }
}
