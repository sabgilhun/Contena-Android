package com.sabgil.contena.presenter.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue.COMPLEX_UNIT_PX
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.databinding.WidgetSwitchTextViewBinding

class SwitchTextView : FrameLayout {

    private lateinit var binding: WidgetSwitchTextViewBinding

    var switchText: String
        set(value) {
            binding.switchTextView.text = value
        }
        get() = binding.switchTextView.text.toString()

    var switchTextSize: Int
        set(value) {
            binding.switchTextView.setTextSize(COMPLEX_UNIT_PX, value.toFloat())
        }
        get() = binding.switchTextView.textSize.toInt()

    @ColorRes
    var switchTextColor: Int = 0
        set(value) {
            binding.switchTextView.setTextColor(value)
            field = value
        }

    var isChecked: Boolean = false
        set(value) {
            binding.switchView.isChecked = value
            field = value
        }

    var switchStateChangeListener: ((Boolean) -> Unit)? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
        readAttrs(attrs)
    }

    private fun initView() {
        binding = WidgetSwitchTextViewBinding.inflate(
            context.layoutInflater, this, true
        )

        binding.switchView.setOnCheckedChangeListener { _, isChecked ->
            switchStateChangeListener?.invoke(isChecked)
        }
    }

    private fun readAttrs(attrs: AttributeSet?) {
        if (attrs == null) return

        val array = context.obtainStyledAttributes(attrs, R.styleable.SwitchTextView)

        switchText = array.getString(R.styleable.SwitchTextView_switchText).orEmpty()

        switchTextSize =
            array.getDimensionPixelSize(R.styleable.SwitchTextView_switchTextSize, DEFAULT_TEXT_SP)

        switchTextColor =
            array.getColor(
                R.styleable.SwitchTextView_switchTextColor,
                Color.parseColor(DEFAULT_TEXT_COLOR)
            )

        isChecked = array.getBoolean(R.styleable.SwitchTextView_switchOn, false)

        array.recycle()
    }

    companion object {
        private const val DEFAULT_TEXT_SP = 12

        private const val DEFAULT_TEXT_COLOR = "#000000"
    }
}