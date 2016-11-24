package com.r21nomi.qiitaclientandroid.ui.view

import android.content.Context
import android.graphics.Typeface
import timber.log.Timber
import java.util.*

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
class TypefaceCache {
    private var cache: MutableMap<String, Typeface>

    constructor() {
        this.cache = WeakHashMap<String, Typeface>()
    }

    companion object {
        private val sSingleton = TypefaceCache()

        fun getInstance(): TypefaceCache {
            return sSingleton
        }
    }

    fun getTypeface(resourceId: Int, context: Context): Typeface {
        val fontPath = context.getString(resourceId)
        return getTypeface(fontPath, context)
    }

    fun getTypeface(fontPath: String, context: Context): Typeface {
        var typeface: Typeface? = cache[fontPath]
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.assets, fontPath)
                cache.put(fontPath, typeface)
            } catch (e: RuntimeException) {
                Timber.e(e, "Failed to load font. Use system font")
                typeface = Typeface.DEFAULT
            }

        }
        return typeface!!
    }
}