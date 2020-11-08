package com.sabgil.contena.presenter.web

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebViewClient
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.startWith
import com.sabgil.contena.databinding.ActivityWebViewBinding
import com.sabgil.contena.presenter.base.BaseActivity


class WebViewActivity : BaseActivity<ActivityWebViewBinding>(R.layout.activity_web_view) {

    private val pageUrl: String by lazy { intent.getStringExtra(PAGE_URL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWebView()
        setupViews()
        binding.webView.loadUrl(pageUrl)
    }

    private fun setupViews() {
        with(binding) {
            backButton.setOnClickListener { finish() }
            openBrowserButton.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(pageUrl)))}
        }
    }

    private fun setupWebView() {
        binding.webView.webViewClient = WebViewClient()
        with(binding.webView.settings) {
            javaScriptCanOpenWindowsAutomatically = false
            loadWithOverviewMode = true
            useWideViewPort = true
            defaultFixedFontSize = 14
        }
    }

    companion object {
        private const val PAGE_URL = "page_url"

        fun start(activity: Activity, pageUrl: String) =
            activity.startWith(WebViewActivity::class, PAGE_URL to pageUrl)
    }
}