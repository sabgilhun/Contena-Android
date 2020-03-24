package com.sabgil.contena.presenter.home.fragments.tabmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

abstract class BaseTabFragment<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : TabFragment() {

    protected lateinit var binding: B
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    protected fun <VM : ViewModel> getViewModel(clazz: KClass<VM>): VM =
        ViewModelProvider(viewModelStore, viewModelFactory).get(clazz.java)

    protected fun <VM : ViewModel> getSharedViewModel(clazz: KClass<VM>): VM =
        ViewModelProvider(requireActivity().viewModelStore, viewModelFactory).get(clazz.java)
}