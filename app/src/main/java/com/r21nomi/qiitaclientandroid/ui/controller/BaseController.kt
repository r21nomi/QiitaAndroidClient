package com.r21nomi.qiitaclientandroid.ui.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.r21nomi.qiitaclientandroid.Application
import com.r21nomi.qiitaclientandroid.di.component.ApplicationComponent
import com.r21nomi.qiitaclientandroid.di.component.ControllerComponent
import com.r21nomi.qiitaclientandroid.di.component.DaggerControllerComponent
import com.r21nomi.qiitaclientandroid.di.module.ControllerModule
import rx.subscriptions.CompositeSubscription

/**
 * Created by Ryota Niinomi on 2016/11/25.
 */
abstract class BaseController : Controller() {

    private var controllerComponent: ControllerComponent? = null
    protected var subscriptionsOnDestroy: CompositeSubscription? = null

    protected abstract fun injectDependency(component: ControllerComponent)
    protected abstract fun getLayout() : Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        controllerComponent = DaggerControllerComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .controllerModule(ControllerModule())
                .build()

        injectDependency(controllerComponent!!)

        return inflater.inflate(getLayout(), container, false)
    }

    override fun onDestroyView(view: View) {
        subscriptionsOnDestroy?.unsubscribe()
        super.onDestroyView(view)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return (applicationContext as Application).getComponent()
    }
}