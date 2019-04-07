package com.burger.bigburger.utils.customviews

import android.content.Context
import android.graphics.Typeface
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet

class ProximaNovaSemiBoldTextView  : AppCompatTextView {

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        if (!isInEditMode) {

            typeface = Typeface.createFromAsset(context.assets, "fonts/proxima_nova_semi_bold.ttf")
        }
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context)
}