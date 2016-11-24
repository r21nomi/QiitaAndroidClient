package com.r21nomi.qiitaclientandroid.model

import android.content.Context
import com.r21nomi.qiitaclientandroid.model.api.ApiClient
import com.r21nomi.qiitaclientandroid.model.entity.Tag2
import rx.Observable
import rx.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ryota Niinomi on 2016/11/23.
 */
@Singleton
class TagModel {
    private val context: Context
    private val apiClient: ApiClient

    @Inject
    constructor(context: Context, apiClient: ApiClient) {
        this.context = context
        this.apiClient = apiClient
    }

    fun getTags(): Observable<List<Tag2>> {
        return apiClient
                .getTags(1, 20, "count")
                .subscribeOn(Schedulers.io())
    }
}