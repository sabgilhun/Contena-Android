package com.sabgil.contena.presenter.postdetail.adapter

import android.content.Context
import com.sabgil.contena.common.adapter.MultiViewTypeAdapter
import com.sabgil.contena.common.adapter.ViewTypeMap
import com.sabgil.contena.common.adapter.multiViewType
import com.sabgil.contena.common.adapter.viewType
import com.sabgil.contena.databinding.ItemNewProductBinding
import com.sabgil.contena.presenter.postdetail.DetailNewProduct
import com.sabgil.contena.presenter.postdetail.activity.PostDetailActivity

class NewProductAdapter(
    context: Context,
    private val handler: PostDetailActivity.Handler
) : MultiViewTypeAdapter() {

    override val viewTypeMap: ViewTypeMap = multiViewType(context) {
        viewType<DetailNewProduct, ItemNewProductBinding> {
            onBind { item, binding, _ ->
                with(binding) {
                    newProduct = item
                    handler = this@NewProductAdapter.handler
                }
            }
        }
    }
}
