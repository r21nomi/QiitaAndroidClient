package com.r21nomi.qiitaclientandroid.model.entity

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
class User(
        val id: String,
        val name: String,
        val description: String,
        val profile_image_url: String,
        val githubLoginName : String,
        val facebookId : String,
        val twitterScreenName : String
) {
    fun getUserName() : String {
        when {
            name.isNotBlank() -> return name
            id.isNotBlank() -> return id
            githubLoginName.isNotBlank() -> return githubLoginName
            twitterScreenName.isNotBlank() -> return twitterScreenName
            facebookId.isNotBlank() -> return facebookId
            else -> return ""
        }
    }
}