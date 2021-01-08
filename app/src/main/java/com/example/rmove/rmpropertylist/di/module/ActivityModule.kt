package com.example.rmove.rmpropertylist.di.module

import android.app.Activity
import com.example.rmove.rmpropertylist.api.PropertyListApi
import com.example.rmove.rmpropertylist.ui.PropertyListContract
import com.example.rmove.rmpropertylist.ui.PropertyListPresenter
import com.example.rmove.rmpropertylist.utils.SchedulerHelper
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun providePropertyListPresenter(
        propertyListApi: PropertyListApi,
        schedulerHelper: SchedulerHelper
    ): PropertyListContract.PropertyListPresenter {
        return PropertyListPresenter(propertyListApi, schedulerHelper)
    }

}