package com.sabgil.contena.presenter.postdetail.activity

import android.app.Activity
import android.os.Bundle
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.startWith
import com.sabgil.contena.databinding.ActivityPostDetailBinding
import com.sabgil.contena.presenter.base.BaseActivity
import com.sabgil.contena.presenter.postdetail.viewmodel.PostDetailViewModel

class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>(R.layout.activity_post_detail) {

    private val viewModel: PostDetailViewModel by lazy { getViewModel(PostDetailViewModel::class) }

    private val postId: String by lazy { requireNotNull(intent.getStringExtra(POST_ID)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {

        private const val POST_ID = "postId"

        fun start(activity: Activity, postId: Long) {
            activity.startWith(PostDetailActivity::class) {
                putExtra(POST_ID, postId)
            }
        }
    }
}