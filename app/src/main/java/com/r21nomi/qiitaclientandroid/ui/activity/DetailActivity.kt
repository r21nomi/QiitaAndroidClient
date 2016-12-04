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

        fun createIntent(context: Context, url: String) = Intent(context, DetailActivity::class.java)
                .apply {
                    putExtra(URL, url)
                }
    }

    val webView by lazy {
        (findViewById(R.id.webView) as WebView).apply {
            setWebViewClient(WebViewClient())
            settings?.javaScriptEnabled = true
        }
    }

    override fun injectDependency(component: ActivityComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        intent.getStringExtra(URL)?.let { webView.loadUrl(it) }
    }
}
