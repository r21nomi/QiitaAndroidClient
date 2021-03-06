package com.r21nomi.qiitaclientandroid.di.component

import com.r21nomi.qiitaclientandroid.di.ActivityScope
import com.r21nomi.qiitaclientandroid.di.module.ActivityModule
import com.r21nomi.qiitaclientandroid.ui.activity.DetailActivity
import com.r21nomi.qiitaclientandroid.ui.activity.LoginActivity
import com.r21nomi.qiitaclientandroid.ui.activity.MainActivity
import com.r21nomi.qiitaclientandroid.ui.activity.OAuthActivity
import dagger.Component

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
@ActivityScope
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(ActivityModule::class)
)
interface ActivityComponent {
    fun inject(activity: LoginActivity)
    fun inject(activity: OAuthActivity)
    fun inject(activity: MainActivity)
    fun inject(activity: DetailActivity)
}