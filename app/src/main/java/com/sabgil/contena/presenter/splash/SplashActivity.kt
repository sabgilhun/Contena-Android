package com.sabgil.contena.presenter.splash

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.onAnimationEnd
import com.sabgil.contena.common.ext.visible
import com.sabgil.contena.databinding.ActivitySplashBinding
import com.sabgil.contena.presenter.base.BaseActivity
import com.sabgil.contena.presenter.home.activity.HomeActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel: SplashViewModel by lazy { getViewModel(SplashViewModel::class) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startSplashAnimation()
        viewModel.setupObserver()
    }

    private fun SplashViewModel.setupObserver() {
        goNext.registerObserver {
            checkLifeCycleAndGoHome()
        }
        getTokenAndGoNext.registerObserver {
            getFireBaseId { checkLifeCycleAndGoHome() }
        }
    }

    private fun startSplashAnimation() {
        binding.logoImage.animate().apply {
            duration = 1000
            onAnimationEnd {
                viewModel.checkFcmToken()
            }
        }.start()
    }

    private fun checkLifeCycleAndGoHome() {
        if (this.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            HomeActivity.start(this)
        }
    }

    private fun getFireBaseId(goNext: () -> Unit) {
        binding.loading.visible = true
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                Thread.sleep(500)
                binding.loading.visible = false
                task.result?.token?.let {
                    viewModel.saveToken(it)
                    goNext()
                } ?: finish()
            })
    }
}