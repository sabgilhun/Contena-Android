package com.sabgil.contena.presenter.splash

import android.os.Bundle
import android.os.Handler
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
        setupObserver()
    }

    private fun setupObserver() {
        with(viewModel) {
            goNext.registerObserver {
                checkLifeCycleAndGoHome()
            }
            getTokenAndGoNext.registerObserver {
                getFireBaseId { checkLifeCycleAndGoHome() }
            }
        }
    }

    private fun startSplashAnimation() {
        binding.logoImage.animate().apply {
            duration = SPLASH_ANIMATION_DURATION
            onAnimationEnd {
                viewModel.checkFcmToken()
            }
        }.start()
    }

    private fun checkLifeCycleAndGoHome() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            HomeActivity.start(this)
        } else {
            Handler().postDelayed({ checkLifeCycleAndGoHome() }, SPLASH_ANIMATION_DURATION)
        }
    }

    private fun getFireBaseId(goNext: () -> Unit) {
        binding.loadingProgressBar.visible = true
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                binding.loadingProgressBar.visible = false
                task.result?.token?.let {
                    viewModel.saveToken(it)
                    goNext()
                } ?: finish()
            })
    }

    companion object {
        private const val SPLASH_ANIMATION_DURATION: Long = 2000
    }
}