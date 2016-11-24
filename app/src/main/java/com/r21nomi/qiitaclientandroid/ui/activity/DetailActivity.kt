package com.r21nomi.qiitaclientandroid.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.di.component.ActivityComponent

class DetailActivity : BaseActivity() {

    companion object {
        private val URL = "url"

        fun createIntent(context: Context, url: String): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(URL, url)
            return intent
        }
    }

    var webView: WebView? = null

    override fun injectDependency(component: ActivityComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        webView = findViewById(R.id.webView) as WebView

        webView?.setWebViewClient(WebViewClient())
        webView?.loadUrl(intent.getStringExtra(URL))
        webView?.settings?.javaScriptEnabled = true
    }
}
