package com.r21nomi.qiitaclientandroid

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.r21nomi.qiitaclientandroid.di.component.ApplicationComponent
import com.r21nomi.qiitaclientandroid.di.component.DaggerApplicationComponent
import com.r21nomi.qiitaclientandroid.di.module.ApplicationModule
import timber.log.Timber

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
open class Application: Application() {

    private val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)
    }

    fun getComponent() : ApplicationComponent {
        return applicationComponent
    }
}