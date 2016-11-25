package com.r21nomi.qiitaclientandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.model.LoginModel

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val accessToken = LoginModel.getAccessToken(this)
        if (accessToken != "") {
            startMainActivity()
        } else {
            startLoginActivity()
        }
    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
