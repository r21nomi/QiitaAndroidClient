package com.r21nomi.qiitaclientandroid.model

import android.content.Context
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.model.api.ApiClient
import com.r21nomi.qiitaclientandroid.model.entity.Tag
import rx.Observable
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ryota Niinomi on 2016/11/23.
 */
@Singleton
class TagModel @Inject constructor(val context: Context, val apiClient: ApiClient) {

    fun fetchTags(page: Int, count: Int): Observable<List<Tag>> = apiClient
            .getTags(page, count, "count")
            .subscribeOn(Schedulers.io())
            .onErrorReturn {
                Timber.e(it, it.message)
                throw IllegalStateException(context.resources.getString(R.string.error))
            }
}