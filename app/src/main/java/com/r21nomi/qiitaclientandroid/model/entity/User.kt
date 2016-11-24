package com.r21nomi.qiitaclientandroid.model.entity

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
class User {

    var id: String
    var name: String
    var description: String

    constructor(description: String, id: String, name: String) {
        this.description = description
        this.id = id
        this.name = name
    }
}