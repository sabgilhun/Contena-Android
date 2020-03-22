package com.sabgil.contena.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sabgil.contena.R
import kotlin.reflect.KClass

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : InjectActivity() {

    protected lateinit var binding: B
        private set

    private val loadingDialog: AlertDialog by lazy {
        AlertDialog.Builder(this, R.style.LoadingDialog)
            .setCancelable(false)
            .setView(R.layout.widget_progress_bar)
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
    }

    protected fun <VM : BaseViewModel> getViewModel(clazz: KClass<VM>): VM {
        val viewModel = ViewModelProvider(viewModelStore, viewModelFactory).get(clazz.java)
        observingBaseViewModel(viewModel)
        return viewModel
    }

    private fun observingBaseViewModel(baseViewModel: BaseViewModel) {
        baseViewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) loadingDialog.show() else loadingDialog.hide()
        })
    }
}


