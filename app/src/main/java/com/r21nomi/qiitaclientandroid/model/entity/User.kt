package com.r21nomi.qiitaclientandroid.model.entity

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
class User {

    var id: String
    var name: String
    var description: String
    var profile_image_url: String

    constructor(description: String, id: String, name: String, profile_image_url: String) {
        this.description = description
        this.id = id
        this.name = name
        this.profile_image_url = profile_image_url
    }
}