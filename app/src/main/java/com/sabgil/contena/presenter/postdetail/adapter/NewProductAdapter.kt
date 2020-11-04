package com.sabgil.contena.presenter.postdetail.adapter

import android.content.Context
import com.sabgil.contena.common.adapter.*
import com.sabgil.contena.databinding.ItemNewProductBinding
import com.sabgil.contena.presenter.postdetail.DetailNewProduct

class NewProductAdapter(context: Context) : MultiViewTypeAdapter() {

    override val viewTypeMap: ViewTypeMap = multiViewType(context) {
        viewType<DetailNewProduct, ItemNewProductBinding> {
            onBind { item, binding, _ ->
                binding.newProduct = item
            }
        }
    }
}
