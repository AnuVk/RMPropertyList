package com.example.rmove.rmpropertylist.di.component

import com.example.rmove.rmpropertylist.RMApp
import com.example.rmove.rmpropertylist.api.PropertyListApi
import com.example.rmove.rmpropertylist.di.module.ApplicationModule
import com.example.rmove.rmpropertylist.utils.SchedulerHelper
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: RMApp)

    fun getPropertyListApi(): PropertyListApi

    fun getSchedulerHelper(): SchedulerHelper
}
