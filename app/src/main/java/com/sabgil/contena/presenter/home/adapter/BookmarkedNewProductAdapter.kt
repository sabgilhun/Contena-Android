package com.sabgil.contena.presenter.home.adapter

import android.content.Context
import com.sabgil.contena.common.adapter.MultiViewTypeAdapter
import com.sabgil.contena.common.adapter.type
import com.sabgil.contena.common.adapter.viewTypeMapStore
import com.sabgil.contena.databinding.ItemBookmarkedNewProductBinding
import com.sabgil.contena.presenter.home.fragment.NewProductBookmarkFragment
import com.sabgil.contena.presenter.home.model.BookmarkedNewProductItem

class BookmarkedNewProductAdapter(
    context: Context,
    private val handler: NewProductBookmarkFragment.Handler
) : MultiViewTypeAdapter() {

    override val viewTypeMapStore = context.viewTypeMapStore {
        type<BookmarkedNewProductItem, ItemBookmarkedNewProductBinding> {
            onBind { item, binding ->
                with(binding) {
                    newProduct = item
                    handler = this@BookmarkedNewProductAdapter.handler
                }
            }
        }
    }
}
