package com.sabgil.contena.presenter.postdetail.adapter

import android.content.Context
import com.sabgil.contena.common.adapter.MultiViewTypeAdapter
import com.sabgil.contena.common.adapter.type
import com.sabgil.contena.common.adapter.viewTypeMapStore
import com.sabgil.contena.databinding.ItemNewProductBinding
import com.sabgil.contena.presenter.postdetail.DetailNewProduct
import com.sabgil.contena.presenter.postdetail.activity.PostDetailActivity

class NewProductAdapter(
    context: Context,
    private val handler: PostDetailActivity.Handler
) : MultiViewTypeAdapter() {

    override val viewTypeMapStore = context.viewTypeMapStore {
        type<DetailNewProduct, ItemNewProductBinding> {
            onBind { item, binding ->
                with(binding) {
                    newProduct = item
                    handler = this@NewProductAdapter.handler
                }
            }
        }
    }
}
