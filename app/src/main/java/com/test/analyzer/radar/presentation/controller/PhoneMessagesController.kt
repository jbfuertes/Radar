package com.test.analyzer.radar.presentation.controller

import com.airbnb.epoxy.EpoxyController
import com.test.analyzer.radar.presentation.model.phoneMeasurements

class PhoneMessagesController : EpoxyController(){

    private var measurements = emptyList<Pair<String, Int>>()

    override fun buildModels() {
        measurements.forEachIndexed { index, pair ->
            phoneMeasurements {
                id("${index}_${pair.first}")
                occurrence(pair.second)
                uuid(pair.first)
            }
        }
    }

    fun setPhoneMeasurements(measurements: List<Pair<String, Int>>) {
        this.measurements = measurements
        requestModelBuild()
    }
}