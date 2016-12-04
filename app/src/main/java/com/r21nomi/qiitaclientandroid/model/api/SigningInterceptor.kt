package com.r21nomi.qiitaclientandroid.model.api

import android.content.Context
import com.r21nomi.qiitaclientandroid.model.getAccessToken
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
class SigningInterceptor(val context: Context) : Interceptor {

    var accessToken: String ?= null

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = addDefaultHeaders(chain.request().newBuilder())
        return chain.proceed(builder.build())
    }

    fun addDefaultHeaders(builder: Request.Builder): Request.Builder {
        builder.addHeader("Authorization", "Bearer " + getToken())
        return builder
    }

    private fun getToken() = accessToken ?: getAccessToken(context)
}