package com.r21nomi.qiitaclientandroid.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.model.getAccessToken

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        getAccessToken(this)?.run { startMainActivity() } ?: startLoginActivity()
    }

    private fun startLoginActivity() {
        startActivity(LoginActivity.createIntent(this))
        finish()
    }

    private fun startMainActivity() {
        startActivity(MainActivity.createIntent(this))
        finish()
    }
}
