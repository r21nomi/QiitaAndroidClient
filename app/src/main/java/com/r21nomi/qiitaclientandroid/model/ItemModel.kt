package com.r21nomi.qiitaclientandroid.model

import com.r21nomi.qiitaclientandroid.model.api.ApiClient
import com.r21nomi.qiitaclientandroid.model.entity.Item
import rx.Observable
import rx.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
@Singleton
class ItemModel {

    private val apiClient: ApiClient

    @Inject
    constructor(apiClient: ApiClient) {
        this.apiClient = apiClient
    }

    fun getItems(page: Int, perPage: Int, query: String): Observable<List<Item>> {
        return apiClient
                .getItems(page, perPage, query)
        .subscribeOn(Schedulers.io())
    }
}