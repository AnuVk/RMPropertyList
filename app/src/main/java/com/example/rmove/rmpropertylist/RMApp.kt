package com.example.rmove.rmpropertylist

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.rmove.rmpropertylist.di.component.ApplicationComponent
import com.example.rmove.rmpropertylist.di.component.DaggerApplicationComponent
import com.example.rmove.rmpropertylist.di.module.ApplicationModule

class RMApp : MultiDexApplication() {
    lateinit var appModule: ApplicationModule

    companion object {
        lateinit var instance: RMApp private set
        lateinit var appComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        setUp()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun setUp() {
        appModule = ApplicationModule(this)
        appComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(appModule)
            .build()
        appComponent.inject(this)
    }
}