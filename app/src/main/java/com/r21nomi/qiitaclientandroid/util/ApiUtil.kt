package com.r21nomi.qiitaclientandroid.util

/**
 * Created by Ryota Niinomi on 2016/11/25.
 */
fun getUrlWithQueryParams(baseUrl: String, params: List<Pair<String, String>>): String =
        params.foldIndexed(baseUrl, {i, url, param -> "$url${if (i == 0) '?' else '&' }${param.first}=${param.second}"})