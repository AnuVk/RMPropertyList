package com.example.rmove.rmpropertylist.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerHelper {
    fun getMainThreadScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    fun getBackgroundScheduler(): Scheduler {
        return Schedulers.newThread()
    }

    fun getIoScheduler(): Scheduler {
        return Schedulers.io()
    }
}