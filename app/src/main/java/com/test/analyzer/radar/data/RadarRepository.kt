package com.test.analyzer.radar.data

import com.example.radar.RadarMeasurements
import io.reactivex.rxjava3.core.Observable

interface RadarRepository {

    fun wifiSignal(): Observable<List<RadarMeasurements>>

    fun phoneMeasurements(): Observable<RadarMeasurements>

    fun phoneMeasurementsOccurrence(): Observable<List<Pair<String, Int>>>
}