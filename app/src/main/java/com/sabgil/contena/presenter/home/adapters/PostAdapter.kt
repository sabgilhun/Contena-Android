package com.sabgil.contena.presenter.home.adapters

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sabgil.contena.R
import com.sabgil.contena.commons.ext.layoutInflater
import com.sabgil.contena.databinding.ItemPostBinding
import com.sabgil.contena.domain.model.Post

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val postItems: MutableList<Post> = mutableListOf()

    fun addAll(postList: List<Post>) {
        postItems.clear()
        postItems.addAll(postList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            DataBindingUtil.inflate(
                parent.context.layoutInflater,
                R.layout.item_post,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = postItems.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.binding.post = postItems[position]
    }

    class PostViewHolder(
        val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
