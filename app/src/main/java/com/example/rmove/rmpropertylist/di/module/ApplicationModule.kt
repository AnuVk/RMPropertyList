package com.example.rmove.rmpropertylist.di.module

import android.app.Application
import com.example.rmove.rmpropertylist.RMApp
import com.example.rmove.rmpropertylist.api.PropertyListApi
import com.example.rmove.rmpropertylist.utils.BASE_URL
import com.example.rmove.rmpropertylist.utils.SchedulerHelper
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApplicationModule(private val rmApp: RMApp) {

    @Provides
    fun provideRMApplication(): Application {
        return rmApp
    }

    @Provides
    internal fun providePropertyApi(retrofit: Retrofit): PropertyListApi {
        return retrofit.create(PropertyListApi::class.java)
    }

    @Provides
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Provides
    fun provideSchedulerHelper(): SchedulerHelper {
        return SchedulerHelper()
    }
}