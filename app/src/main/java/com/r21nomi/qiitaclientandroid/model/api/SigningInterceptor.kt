package com.r21nomi.qiitaclientandroid.model.api

import android.content.Context
import com.r21nomi.qiitaclientandroid.R
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
class SigningInterceptor : Interceptor {

    val ACCESS_TOKEN: String

    constructor(context: Context) {
        ACCESS_TOKEN = context.getString(R.string.access_token)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = addDefaultHeaders(chain.request().newBuilder())
        return chain.proceed(builder.build())
    }

    fun addDefaultHeaders(builder: Request.Builder): Request.Builder {
        builder.addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
        return builder
    }
}