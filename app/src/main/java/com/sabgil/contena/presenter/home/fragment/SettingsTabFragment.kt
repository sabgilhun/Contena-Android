package com.sabgil.contena.presenter.home.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.sabgil.contena.R
import com.sabgil.contena.databinding.FragmentSettingsTabBinding

class SettingsTabFragment :
    BaseTabFragment<FragmentSettingsTabBinding>(R.layout.fragment_settings_tab) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.handler = Handler()
    }

    override fun refreshTab() {
        /* Do Nothing */
    }

    override fun scrollOnTop() {
        /* Do Nothing */
    }

    inner class Handler {

        fun sendEmail() {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "plain/Text"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("sabgilhun@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "문의합니다.")
            }
            startActivity(intent)
        }

        fun evaluationApplication() {
            val uri = Uri.parse("https://play.google.com/store/apps/details")
                .buildUpon()
                .appendQueryParameter("id", "com.sabgil.contena")
                .build()

            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = uri
                setPackage("com.android.vending")
            }

            startActivity(intent)
        }
    }
}