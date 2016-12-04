package com.r21nomi.qiitaclientandroid.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by Ryota Niinomi on 2016/11/24.
 */
@Module
class ApplicationModule(val application: Application) {

    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Provides
    fun provideApplicationContext(): Context {
        return application.applicationContext
    }
}