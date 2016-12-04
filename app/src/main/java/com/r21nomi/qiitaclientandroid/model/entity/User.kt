package com.r21nomi.qiitaclientandroid.model.entity

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
data class User(
        val id: String,
        val name: String,
        val description: String,
        val profile_image_url: String,
        val githubLoginName : String,
        val facebookId : String,
        val twitterScreenName : String
) {
    fun getUserName() = when {
        name.isNotBlank() -> name
        id.isNotBlank() -> id
        githubLoginName.isNotBlank() -> githubLoginName
        twitterScreenName.isNotBlank() -> twitterScreenName
        facebookId.isNotBlank() -> facebookId
        else -> ""
    }
}