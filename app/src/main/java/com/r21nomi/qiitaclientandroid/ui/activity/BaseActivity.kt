package com.r21nomi.qiitaclientandroid.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.r21nomi.qiitaclientandroid.Application
import com.r21nomi.qiitaclientandroid.di.component.ActivityComponent
import com.r21nomi.qiitaclientandroid.di.component.ApplicationComponent
import com.r21nomi.qiitaclientandroid.di.component.DaggerActivityComponent
import rx.subscriptions.CompositeSubscription

abstract class BaseActivity: AppCompatActivity() {

    private var activityComponent: ActivityComponent? = null

    protected var subscriptionsOnDestroy: CompositeSubscription? = null

    protected abstract fun injectDependency(component: ActivityComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()

        injectDependency(activityComponent!!)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return (application as Application).getComponent()
    }

    override fun onDestroy() {
        subscriptionsOnDestroy?.unsubscribe()
        super.onDestroy()
    }
}
