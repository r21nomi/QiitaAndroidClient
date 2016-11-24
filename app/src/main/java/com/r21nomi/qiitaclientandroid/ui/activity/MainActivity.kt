package com.r21nomi.qiitaclientandroid.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.databinding.ActivityMainBinding
import com.r21nomi.qiitaclientandroid.di.component.ActivityComponent
import com.r21nomi.qiitaclientandroid.ui.controller.MainController

class MainActivity : BaseActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
    private lateinit var router: Router

    override fun injectDependency(component: ActivityComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rootView = binding.rootView ?: return

        router = Conductor.attachRouter(this, rootView, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(MainController()))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }
}
