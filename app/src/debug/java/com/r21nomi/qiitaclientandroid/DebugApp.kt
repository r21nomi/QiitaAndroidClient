package com.r21nomi.qiitaclientandroid

import com.facebook.stetho.Stetho

/**
 * Created by Ryota Niinomi on 2016/11/23.
 */
class DebugApp : App() {
    override fun onCreate() {
        super.onCreate()

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        )
    }
}