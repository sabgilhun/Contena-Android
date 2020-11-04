package com.sabgil.contena.presenter.postdetail.adapter

import com.sabgil.contena.R
import com.sabgil.contena.common.adapter.*
import com.sabgil.contena.databinding.ItemNewProductBinding
import com.sabgil.contena.presenter.postdetail.DetailNewProduct

class NewProductAdapter : MultiViewTypeAdapter() {

    override val viewTypeMap: ViewTypeMap = multiViewType {
        viewType<DetailNewProduct, ItemNewProductBinding>(R.layout.item_new_product) {
            onBind { item, binding, _ ->
                binding.newProduct = item
            }
        }
    }
}
