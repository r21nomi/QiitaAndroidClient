package com.r21nomi.qiitaclientandroid.model.entity

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
data class Item(
        val id: String,
        val title: String,
        val rendered_body: String,
        val body: String,
        val coediting: Boolean,
        val created_at: String,
        val updated_at: String,
        val tags: List<Tag>,
        val url: String,
        val user: User
)