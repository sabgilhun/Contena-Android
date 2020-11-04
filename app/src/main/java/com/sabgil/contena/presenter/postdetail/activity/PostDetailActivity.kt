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
    private val uploadDate: String by lazy { intent.getStringExtra(UPLOAD_DATE) }

    private lateinit var adapter: NewProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.uploadDate = uploadDate

        setupBinding()
        setupNewItemRecyclerView()

        setupObserver()
        viewModel.loadNewItemList(postId)
    }

    private fun setupBinding() {
        binding.backButton.setOnClickListener { finish() }
    }

    private fun setupNewItemRecyclerView() {
        adapter = NewProductAdapter(this)
        binding.newItemRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.newItemRecyclerView.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.newItemList.registerNonNullObserver(adapter::replaceAll)
    }

    companion object {
        private const val POST_ID = "postId"
        private const val UPLOAD_DATE = "uploadDate"

        fun start(
            activity: Activity,
            postId: Long,
            uploadDate: String
        ) {
            activity.startWith(
                PostDetailActivity::class,
                POST_ID to postId, UPLOAD_DATE to uploadDate
            )
        }
    }
}