package com.test.analyzer.radar.presentation.common

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SchedulerImpl: Scheduler {
    override val ui = AndroidSchedulers.mainThread()
    override val io = Schedulers.io()
}