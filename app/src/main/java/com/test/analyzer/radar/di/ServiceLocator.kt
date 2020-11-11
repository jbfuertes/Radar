package com.test.analyzer.radar.di

import com.example.radar.Radar
import com.test.analyzer.radar.data.RadarRepository
import com.test.analyzer.radar.data.RadarReposityImpl
import com.test.analyzer.radar.presentation.common.Scheduler
import com.test.analyzer.radar.presentation.common.SchedulerImpl

object ServiceLocator {

    private val radar = Radar
    val radarRepository: RadarRepository = RadarReposityImpl(radar)
    val scheduler: Scheduler = SchedulerImpl()

}