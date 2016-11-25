package com.r21nomi.qiitaclientandroid.ui.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.databinding.ActivityLoginBinding
import com.r21nomi.qiitaclientandroid.di.component.ActivityComponent
import com.r21nomi.qiitaclientandroid.model.LoginModel
import com.r21nomi.qiitaclientandroid.util.ViewUtil
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class LoginActivity : BaseActivity() {

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
            val accessToken = LoginModel.getAccessToken(this)
            if (accessToken != "") {
                startMainActivity()
            } else {
                startOauth()
            }
        }

        binding.startButton.setOnClickListener {
            LoginModel.setAccessToken(this, getString(R.string.default_access_token))
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OAuthActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val code = data?.getStringExtra(OAuthActivity.KEY_CODE)

            if (code == null) return

            loginModel
                    .fetchAccessTokens(code)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ accessToken ->
                        startMainActivity()
                    }, { throwable ->
                        ViewUtil.showSnackBar(this, throwable.message ?: return@subscribe)
                    })
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun startOauth() {
        val intent: Intent = Intent(this, OAuthActivity::class.java)
        startActivityForResult(intent, OAuthActivity.REQUEST_CODE)
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
