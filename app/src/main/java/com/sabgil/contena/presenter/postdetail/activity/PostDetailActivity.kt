package com.sabgil.contena.presenter.postdetail.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.startWith
import com.sabgil.contena.databinding.ActivityPostDetailBinding
import com.sabgil.contena.presenter.base.BaseActivity
import com.sabgil.contena.presenter.postdetail.adapter.NewProductAdapter
import com.sabgil.contena.presenter.postdetail.viewmodel.PostDetailViewModel

class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>(R.layout.activity_post_detail) {

    private val viewModel: PostDetailViewModel by lazy { getViewModel(PostDetailViewModel::class) }

    private val postId: Long by lazy { intent.getLongExtra(POST_ID, 0L) }
    private val shopName: String by lazy { requireNotNull(intent.getStringExtra(SHOP_NAME)) }
    private val uploadDate: String by lazy { requireNotNull(intent.getStringExtra(UPLOAD_DATE)) }

    private lateinit var adapter: NewProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupNewItemRecyclerView()

        viewModel.setupObserver()
        viewModel.loadNewItemList(postId)
    }

    private fun setupBinding() {
        binding.shopName = shopName
        binding.uploadDate = uploadDate
        binding.backButton.setOnClickListener { finish() }
    }

    private fun setupNewItemRecyclerView() {
        adapter = NewProductAdapter()
        binding.newItemRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.newItemRecyclerView.adapter = adapter
    }

    private fun PostDetailViewModel.setupObserver() {
        newItemList.registerNonNullObserver(adapter::replaceAll)
    }

    companion object {
        private const val POST_ID = "postId"
        private const val SHOP_NAME = "shopName"
        private const val UPLOAD_DATE = "uploadDate"

        fun start(
            activity: Activity,
            postId: Long,
            shopName: String,
            uploadDate: String
        ) {
            activity.startWith(
                PostDetailActivity::class,
                POST_ID to postId, SHOP_NAME to shopName, UPLOAD_DATE to uploadDate
            )
        }
    }
}