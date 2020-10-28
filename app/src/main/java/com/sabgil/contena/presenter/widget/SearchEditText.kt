package com.sabgil.contena.presenter.widget

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.common.ext.visibleOrGone
import com.sabgil.contena.databinding.WidgetSearchEditTextBinding


class SearchEditText : FrameLayout {

    var text: Editable?
        set(value) {
            binding.shopSearchEditText.text = value
        }
        get() = binding.shopSearchEditText.text

    var textChangeListener: ((String) -> Unit)? = null

    private lateinit var binding: WidgetSearchEditTextBinding

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            setVisibleClearButtonIfFocused(binding.shopSearchEditText.isFocused)
            s?.let { textChangeListener?.invoke(it.toString()) }
        }
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        binding = DataBindingUtil.inflate(
            context.layoutInflater,
            R.layout.widget_search_edit_text,
            this,
            true
        )

        if (isInEditMode) return

        binding.shopSearchEditText.addTextChangedListener(textWatcher)

        binding.shopSearchEditText.setOnFocusChangeListener { _, hasFocus ->
            setVisibleClearButtonIfFocused(hasFocus)
        }

        binding.clearTextImageButton.setOnClickListener { clearText() }
    }

    private fun setVisibleClearButtonIfFocused(hasFocus: Boolean) {
        binding.clearTextImageButton.visibleOrGone = hasFocus && text?.length ?: 0 > 0
    }

    private fun clearText() {
        binding.shopSearchEditText.text = null
    }
}