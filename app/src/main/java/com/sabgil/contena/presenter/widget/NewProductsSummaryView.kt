package com.sabgil.contena.presenter.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import com.sabgil.contena.R
import com.sabgil.contena.common.ext.layoutInflater
import com.sabgil.contena.common.ext.setImage
import com.sabgil.contena.common.ext.visible
import com.sabgil.contena.databinding.WidgetNewProductsSummaryViewBinding

class NewProductsSummaryView : FrameLayout {

    private lateinit var binding: WidgetNewProductsSummaryViewBinding

    var imageUrlList: List<String> = emptyList()
        set(value) {
            field = value
            updateImage(value)
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
        binding = WidgetNewProductsSummaryViewBinding.inflate(
            context.layoutInflater,
            this,
            true
        )
    }

    private fun updateImage(imageUrlList: List<String>) {
        val isMoreImage: Boolean
        val frontImageList: List<String>

        if (imageUrlList.size > 9) {
            isMoreImage = true
            frontImageList = imageUrlList.subList(0, 8)
        } else {
            isMoreImage = false
            frontImageList = imageUrlList
        }

        frontImageList.forEachIndexed { index, s ->
            (binding.imageContainer.getChildAt(index) as ImageView).setImage(s)
        }

        binding.indicatorMore.visible = isMoreImage
        binding.indicatorMore.text = context.getString(
            R.string.widget_new_products_summary_view_more_item_format,
            imageUrlList.size - frontImageList.size
        )
    }
}