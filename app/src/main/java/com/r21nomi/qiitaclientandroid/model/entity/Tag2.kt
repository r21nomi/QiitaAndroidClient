package com.r21nomi.qiitaclientandroid.model.entity

/**
 * Created by Ryota Niinomi on 2016/11/23.
 */
class Tag2 {

    val id: String
    val followers_count: Int
    val icon_url: String
    val items_count: Int

    constructor(id: String, followers_count: Int, icon_url: String, items_count: Int) {
        this.followers_count = followers_count
        this.icon_url = icon_url
        this.id = id
        this.items_count = items_count
    }
}