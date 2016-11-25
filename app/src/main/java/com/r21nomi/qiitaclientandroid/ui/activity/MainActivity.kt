package com.r21nomi.qiitaclientandroid.ui.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.databinding.ActivityMainBinding
import com.r21nomi.qiitaclientandroid.di.component.ActivityComponent
import com.r21nomi.qiitaclientandroid.model.LoginModel
import com.r21nomi.qiitaclientandroid.ui.controller.MainController
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
    private lateinit var router: Router

    @Inject
    lateinit var loginModel: LoginModel

    override fun injectDependency(component: ActivityComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rootView = binding.rootView ?: return

        router = Conductor.attachRouter(this, rootView, savedInstanceState)

        val accessToken = LoginModel.getAccessToken(this)
        if (accessToken != "") {
            showItems()
        } else {
            startOauth()
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            val code = data?.getStringExtra(LoginActivity.KEY_CODE)

            if (code == null) return

            loginModel
                    .fetchAccessTokens(code)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ accessToken ->
                        showItems()
                    }, { throwable ->
                        Timber.e(throwable, throwable.message)
                    })
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun startOauth() {
        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivityForResult(intent, 100)
    }

    private fun showItems() {
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(MainController()))
        }
    }
}
