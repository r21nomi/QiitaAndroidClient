package com.r21nomi.qiitaclientandroid.di.component

import com.r21nomi.qiitaclientandroid.di.ControllerScope
import com.r21nomi.qiitaclientandroid.di.module.ControllerModule
import com.r21nomi.qiitaclientandroid.ui.controller.MainController
import dagger.Component

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
@ControllerScope
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(ControllerModule::class)
)
interface ControllerComponent {
    fun inject(controller: MainController)
}