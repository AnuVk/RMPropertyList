package com.example.rmove.rmpropertylist.di.component

import com.example.rmove.rmpropertylist.di.ActivityScope
import com.example.rmove.rmpropertylist.di.module.ActivityModule
import com.example.rmove.rmpropertylist.ui.PropertyActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(propertyActivity: PropertyActivity)

}