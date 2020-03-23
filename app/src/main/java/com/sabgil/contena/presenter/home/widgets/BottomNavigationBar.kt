package com.sabgil.contena.presenter.home.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.sabgil.contena.R
import com.sabgil.contena.commons.ext.layoutInflater
import com.sabgil.contena.databinding.WidgetBottomNavigationBarBinding

class BottomNavigationBar : FrameLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    private lateinit var binding: WidgetBottomNavigationBarBinding

    private fun initView() {
        binding = DataBindingUtil.inflate(
            context.layoutInflater,
            R.layout.widget_bottom_navigation_bar,
            this,
            true
        )
    }
}