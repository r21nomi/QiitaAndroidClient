package com.r21nomi.qiitaclientandroid.model.entity

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
class Item {

    var id: String
    var title: String
    var rendered_body: String
    var coediting: Boolean
    var created_at: String
    var updated_at: String
    var tags: List<Tag>
    var url: String
    var user: User

    constructor(coediting: Boolean, created_at: String, id: String, rendered_body: String, tags: List<Tag>, title: String, updated_at: String, url: String, user: User) {
        this.coediting = coediting
        this.created_at = created_at
        this.id = id
        this.rendered_body = rendered_body
        this.tags = tags
        this.title = title
        this.updated_at = updated_at
        this.url = url
        this.user = user
    }
}