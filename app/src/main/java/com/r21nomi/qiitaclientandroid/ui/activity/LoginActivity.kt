package com.r21nomi.qiitaclientandroid.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.databinding.ActivityLoginBinding
import com.r21nomi.qiitaclientandroid.di.component.ActivityComponent
import com.r21nomi.qiitaclientandroid.model.LoginModel
import com.r21nomi.qiitaclientandroid.model.getAccessToken
import com.r21nomi.qiitaclientandroid.model.setAccessToken
import com.r21nomi.qiitaclientandroid.util.ViewUtil
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    @Inject
    lateinit var loginModel: LoginModel

    private lateinit var binding: ActivityLoginBinding

    override fun injectDependency(component: ActivityComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.loginButton.setOnClickListener {
            getAccessToken(this)?.run { startMainActivity() } ?: startOauth()
        }

        binding.startButton.setOnClickListener {
            setAccessToken(this, getString(R.string.default_access_token))
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OAuthActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val code = data?.getStringExtra(OAuthActivity.KEY_CODE) ?: return

            loginModel
                    .fetchAccessTokens(code)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        startMainActivity()
                    }, {
                        ViewUtil.showSnackBar(this, it.message ?: return@subscribe)
                    })
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun startOauth() {
        startActivityForResult(OAuthActivity.createIntent(this), OAuthActivity.REQUEST_CODE)
    }

    private fun startMainActivity() {
        startActivity(MainActivity.createIntent(this))
        finish()
    }
}
