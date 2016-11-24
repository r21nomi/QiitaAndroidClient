package com.r21nomi.qiitaclientandroid.model.api

import com.r21nomi.qiitaclientandroid.model.entity.Item
import com.r21nomi.qiitaclientandroid.model.entity.Tag2
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by Ryota Niinomi on 2016/11/23.
 */
interface ApiClient {

    @GET("v2/items")
    fun getItems(
            @Query("page") page: Int,
            @Query("per_page") perPage: Int,
            @Query("query") query: String
    ): Observable<List<Item>>

    @GET("v2/tags")
    fun getTags(
            @Query("page") page: Int,
            @Query("per_page") perPage: Int,
            @Query("sort") sort: String
    ): Observable<List<Tag2>>
}