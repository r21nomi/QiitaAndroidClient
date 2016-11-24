package com.r21nomi.qiitaclientandroid.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.r21nomi.qiitaclientandroid.R

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
class CustomFontTextView : TextView {

    private val FONT_FAMILIES = intArrayOf(
            R.string.font_light,
            R.string.font_thin,
            R.string.font_bold
    )

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setFont(context, attrs!!)
    }

    private fun setFont(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView)

        val font = typedArray.getInt(R.styleable.CustomFontTextView_fontType, 0)
        val fontFamily = FONT_FAMILIES[font]

        val typeface = TypefaceCache.getInstance().getTypeface(fontFamily, context)
        setTypeface(typeface)
        typedArray.recycle()
    }
}