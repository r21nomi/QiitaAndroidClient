package com.r21nomi.qiitaclientandroid.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.r21nomi.qiitaclientandroid.R
import com.r21nomi.qiitaclientandroid.databinding.ActivityMainBinding
import com.r21nomi.qiitaclientandroid.di.component.ActivityComponent
import com.r21nomi.qiitaclientandroid.ui.controller.MainController
import com.r21nomi.qiitaclientandroid.ui.controller.TagsController

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

        initToolbar()
        initContent(savedInstanceState)

        binding.bottomNavigation.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId) {
                    R.id.top -> {
                        router.setRoot(RouterTransaction.with(MainController()))
                        return true
                    }
                    R.id.second -> {
                        router.setRoot(RouterTransaction.with(TagsController()))
                        return true
                    }
                    else -> return false
                }
            }
        })
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setTitle(R.string.app_name)
    }

    private fun  initContent(savedInstanceState: Bundle?) {
        val rootView = binding.rootView ?: return

        router = Conductor.attachRouter(this, rootView, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(MainController()))
        }
    }
}
