package com.test.analyzer.radar.presentation.common

import io.reactivex.rxjava3.core.Scheduler

interface Scheduler {
    val ui: Scheduler
    val io: Scheduler
}