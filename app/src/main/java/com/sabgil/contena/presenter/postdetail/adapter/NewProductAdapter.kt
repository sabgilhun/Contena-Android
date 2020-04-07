package com.sabgil.contena.presenter.postdetail.adapter

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.databinding.ItemNewProductBinding
import com.sabgil.contena.domain.model.DetailNewItem

class NewProductAdapter : RecyclerView.Adapter<NewProductAdapter.NewItemViewHolder>() {

    private val newProductList: MutableList<DetailNewItem> = mutableListOf()

    fun replaceAll(newProductList: List<DetailNewItem>) {
        this.newProductList.clear()
        this.newProductList.addAll(newProductList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewItemViewHolder =
        NewItemViewHolder(
            DataBindingUtil.inflate(
                parent.context.layoutInflater,
                R.layout.item_new_product,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = newProductList.size

    override fun onBindViewHolder(holder: NewItemViewHolder, position: Int) {
        holder.binding.newProduct = newProductList[position]
    }

    class NewItemViewHolder(
        val binding: ItemNewProductBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
