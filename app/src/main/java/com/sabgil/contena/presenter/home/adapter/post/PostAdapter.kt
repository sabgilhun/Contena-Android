package com.sabgil.contena.presenter.home.adapter.post

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
    ViewHolderInitializerImpl()
) {

    override fun onBindItemViewHolder(
        item: PostListItem,
        viewHolder: PageManagerViewHolder.ItemViewHolder
    ) {
        (viewHolder as PostItemViewHolder).apply {
            pageChangeObserver.unRegisterConsumer()

            binding.postListItem = item
            binding.currentNewItem = item.newItemList[item.viewingPosition]

            binding.newItemImageViewPager.apply {
                (adapter as NewItemViewPagerAdapter).replaceAll(item.newItemList)
                currentItem = item.viewingPosition
            }

            pageChangeObserver.registerOnChangeConsumer {
                item.viewingPosition = it
                binding.currentNewItem = item.newItemList[it]
            }
        }
    }

    override fun onCreateItemViewHolder(parent: ViewGroup): PageManagerViewHolder.ItemViewHolder {
        val pageChangeObserver = PageChangeObserver()

        val binding = DataBindingUtil.inflate<ItemPostBinding>(
            parent.context.layoutInflater,
            R.layout.item_post,
            parent,
            false
        )

        val viewHolder = PostItemViewHolder(binding, pageChangeObserver)

        binding.newItemImageViewPager.apply {
            adapter = NewItemViewPagerAdapter()

            addOnPageChangeListener(pageChangeObserver)
        }

        binding.imagePageTabLayout.setupWithViewPager(binding.newItemImageViewPager)

        binding.goTotalNewProductTextView.setOnClickListener {
            getItem(viewHolder.adapterPosition)?.let {
                navigator.goToTotalProduction(it.postId, it.shopName, it.uploadDate)
            }
        }

        return viewHolder
    }

    private class PostItemViewHolder(
        val binding: ItemPostBinding,
        val pageChangeObserver: PageChangeObserver
    ) : PageManagerViewHolder.ItemViewHolder(binding.root)

    interface Navigator {

        fun goToTotalProduction(postId: Long, shopName: String, uploadDate: String)

        fun goToOriginWeb()
    }
}
