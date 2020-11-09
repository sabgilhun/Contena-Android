package com.sabgil.contena.presenter.home.adapter

import android.content.Context
import com.sabgil.contena.common.adapter.MultiViewTypeAdapter
import com.sabgil.contena.common.adapter.ViewTypeMap
import com.sabgil.contena.common.adapter.multiViewType
import com.sabgil.contena.common.adapter.viewType
import com.sabgil.contena.databinding.ItemBookmarkedNewProductBinding
import com.sabgil.contena.databinding.ItemNewProductBinding
import com.sabgil.contena.presenter.home.fragment.NewProductBookmarkFragment
import com.sabgil.contena.presenter.home.model.BookmarkedNewProductItem
import com.sabgil.contena.presenter.postdetail.DetailNewProduct
import com.sabgil.contena.presenter.postdetail.activity.PostDetailActivity

class BookmarkedNewProductAdapter(
    context: Context,
    private val handler: NewProductBookmarkFragment.Handler
) : MultiViewTypeAdapter() {

    override val viewTypeMap: ViewTypeMap = multiViewType(context) {
        viewType<BookmarkedNewProductItem, ItemBookmarkedNewProductBinding> {
            onBind { item, binding, _ ->
                with(binding) {
                    newProduct = item
                    handler = this@BookmarkedNewProductAdapter.handler
                }
            }
        }
    }
}
