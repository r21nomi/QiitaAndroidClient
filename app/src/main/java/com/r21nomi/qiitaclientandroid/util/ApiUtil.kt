package com.r21nomi.qiitaclientandroid.util

/**
 * Created by Ryota Niinomi on 2016/11/25.
 */
class ApiUtil {
    companion object {
        fun getUrlWithQueryParams(baseUrl: String, param: List<Pair<String, String>>): String {
            var url: String = baseUrl

            var i: Int = 0
            for (pair in param) {
                if (i == 0) {
                    url += "?"
                } else {
                    url += "&"
                }
                url += (pair.first + "=" + pair.second)
                i++
            }

            return url
        }
    }
}