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
            binding.postListItem = item

            pageObserver.unRegisterObserver()
            binding.newItemViewPager.apply {
                currentItem = item.viewingPosition
                (adapter as NewItemViewPagerAdapter).replaceAll(item.newItemList)
            }
            pageObserver.registerObserver { item.viewingPosition = it }
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

        binding.newItemViewPager.apply {
            adapter = NewItemViewPagerAdapter()
            addOnPageChangeListener(pageChangeObserver)
        }

        return PostItemViewHolder(
            binding,
            pageChangeObserver
        )
    }

    private class PostItemViewHolder(
        val binding: ItemPostBinding,
        val pageObserver: PageChangeObserver
    ) : PageManagerViewHolder.ItemViewHolder(binding.root)
}
