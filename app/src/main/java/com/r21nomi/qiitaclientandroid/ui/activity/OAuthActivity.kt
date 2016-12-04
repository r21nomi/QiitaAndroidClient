package com.r21nomi.qiitaclientandroid.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.databinding.ActivityOauthBinding
import com.r21nomi.qiitaclientandroid.di.component.ActivityComponent
import com.r21nomi.qiitaclientandroid.model.LoginModel
import javax.inject.Inject

class OAuthActivity : BaseActivity() {

    companion object {
        val KEY_CODE = "code"
        val REQUEST_CODE = 10

        fun createIntent(context: Context) = Intent(context, OAuthActivity::class.java)
    }

    @Inject
    lateinit var loginModel: LoginModel

    override fun injectDependency(component: ActivityComponent) {
        component.inject(this)
    }

    val webViewClient: WebViewClient = object : WebViewClient() {
        // FIXME: Fix deprecated method
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (url.startsWith(getString(R.string.oauth_redirect_url))) {
                val uri = Uri.parse(url)
                val code = uri.getQueryParameter("code")
                val result = Intent()
                result.putExtra(KEY_CODE, code)

                setResult(Activity.RESULT_OK, result)
                finish()
                return true
            } else {
                return super.shouldOverrideUrlLoading(view, url)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oauth)

        val binding: ActivityOauthBinding = DataBindingUtil.setContentView(this, R.layout.activity_oauth)

        binding.webView.setWebViewClient(webViewClient)
        binding.webView.loadUrl(loginModel.getOAuthUrl())
    }
}
