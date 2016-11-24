package com.r21nomi.qiitaclientandroid.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.r21nomi.qiitaclientandroid.Application
import com.r21nomi.qiitaclientandroid.di.component.ActivityComponent
import com.r21nomi.qiitaclientandroid.di.component.ApplicationComponent
import com.r21nomi.qiitaclientandroid.di.component.DaggerActivityComponent
import com.r21nomi.qiitaclientandroid.di.module.ActivityModule

abstract class BaseActivity: AppCompatActivity() {

    var activityComponent: ActivityComponent? = null

    protected abstract fun injectDependency(component: ActivityComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(ActivityModule(this))
                .build()

        injectDependency(activityComponent!!)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return (application as Application).applicationComponent!!
    }
}
