package com.r21nomi.qiitaclientandroid.model

import android.content.Context
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.model.api.ApiClient
import com.r21nomi.qiitaclientandroid.util.getUrlWithQueryParams
import rx.Observable
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ryota Niinomi on 2016/11/25.
 */
@Singleton
class LoginModel @Inject constructor(val context: Context, val apiClient: ApiClient) {

    fun fetchAccessTokens(code: String): Observable<String> = apiClient
            .getAccessTokens(
                    context.getString(R.string.client_id),
                    context.getString(R.string.client_secret),
                    code
            )
            .subscribeOn(Schedulers.io())
            .onErrorReturn {
                Timber.e(it, it.message)
                throw IllegalStateException(context.resources.getString(R.string.error))
            }
            .map {
                setAccessToken(context, it.token)
                it.token
            }

    fun getOAuthUrl(): String {
        val params = listOf(
                "client_id" to context.getString(R.string.client_id),
                "scope" to "read_qiita write_qiita_team"
        )

        return getUrlWithQueryParams(
                context.getString(R.string.api_endpoint) + context.getString(R.string.oauth_path),
                params
        )
    }
}

val PREF_NAME = "login_model_pref"
val PREF_KEY_ACCESS_TOKEN = "access_token"

fun getAccessToken(context: Context) : String? = context
        .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        .getString(PREF_KEY_ACCESS_TOKEN, null)

fun setAccessToken(context: Context, accessToken: String) = context
        .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        .edit()
        .putString(PREF_KEY_ACCESS_TOKEN, accessToken)
        .commit()