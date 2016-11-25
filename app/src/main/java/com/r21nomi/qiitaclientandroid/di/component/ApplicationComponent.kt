package com.r21nomi.qiitaclientandroid.di.component

import com.r21nomi.qiitaclientandroid.Application
import com.r21nomi.qiitaclientandroid.di.module.ApiModule
import com.r21nomi.qiitaclientandroid.di.module.ApplicationModule
import com.r21nomi.qiitaclientandroid.model.ItemModel
import com.r21nomi.qiitaclientandroid.model.LoginModel
import com.r21nomi.qiitaclientandroid.model.TagModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
@Singleton
@Component(
        modules = arrayOf(
                ApplicationModule::class,
                ApiModule::class
        )
)
interface ApplicationComponent {
    fun inject(application: Application)

    fun itemModel(): ItemModel
    fun tagModel(): TagModel
    fun loginModel(): LoginModel
}