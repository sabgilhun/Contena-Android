package com.sabgil.contena.ui.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sabgil.contena.R
import com.sabgil.contena.databinding.WidgetProgressBarBinding
import kotlin.reflect.KClass

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : InjectActivity() {

    protected lateinit var binding: B
        private set

    private lateinit var progressBar: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        baseViewSetUp()
    }

    private fun baseViewSetUp() {
        progressBar = DataBindingUtil.inflate<WidgetProgressBarBinding>(
            layoutInflater,
            R.layout.widget_progress_bar,
            binding.root as ViewGroup,
            true
        ).root
    }

    protected fun <VM : BaseViewModel> getViewModel(clazz: KClass<VM>): VM {
        val viewModel = ViewModelProvider(viewModelStore, viewModelFactory).get(clazz.java)
        observingBaseViewModel(viewModel)
        return viewModel
    }

    private fun observingBaseViewModel(baseViewModel: BaseViewModel) {
        baseViewModel.nonBlockingLoading.observe(this, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        baseViewModel.blockingLoading.observe(this, Observer { isLoading ->
            setWindowClickable(isLoading)
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        baseViewModel.showApiErrorMessage.observe(this, Observer {
        })
    }

    private fun setWindowClickable(isClickable: Boolean) {
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE.let { notTouchableFlag ->
            window.setFlags(
                if (isClickable) notTouchableFlag.inv() else notTouchableFlag,
                notTouchableFlag
            )
        }
    }
}


