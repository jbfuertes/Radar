package com.test.analyzer.radar.data

import com.example.radar.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Timed
import java.util.*
import java.util.concurrent.TimeUnit

class RadarReposityImpl(
    private val radar: Radar
) : RadarRepository {

    private val source = Observable.fromPublisher<RadarMeasurements> {
        radar.addListener(object : RadarListener {
            override fun onRadarMeasurementsReceived(radarMeasurements: RadarMeasurements) {
                it.onNext(radarMeasurements)
            }

            override fun onRadarStart(date: Date) {

            }

            override fun onRadarStop(date: Date) {

            }

        })
    }

    override fun wifiSignal(): Observable<List<RadarMeasurements>> {
        return source.filter { it.payloadType == PayloadType.WIFI_SIGNAL }
            .timestamp(TimeUnit.MILLISECONDS)
            .scan(listOf<Timed<RadarMeasurements>>(), { t1, t2 ->
                val newList = t1.toMutableList()
                newList.add(t2)
                newList.asSequence()
                    .groupBy { (it.value().payload as Payload.WifiSignal).content }
                    .toList()
                    .sortedByDescending { it.first }
                    .flatMap { pair ->
                        pair.second.sortedByDescending { it.time() }
                    }.take(3)
            }).map { list -> list.map { it.value() } }
            .throttleFirst(WIFI_UPDATE_INTERVAL, TimeUnit.SECONDS)
    }

    override fun phoneMeasurements(): Observable<RadarMeasurements> {
        return source.filter {
            it.payloadType == PayloadType.PHONE_MESSAGE
        }
    }

    override fun phoneMeasurementsOccurrence(): Observable<List<Pair<String, Int>>> {
        return source.filter { it.payloadType == PayloadType.PHONE_MESSAGE }
            .scan(listOf<RadarMeasurements>(), { t1, t2 ->
                val newList = t1.toMutableList()
                newList.add(t2)
                newList
            }).map { measurements ->
                measurements.groupingBy { it.id.toString() }.eachCount().toList()
            }
    }

    companion object {
        private const val WIFI_UPDATE_INTERVAL = 2L
    }

}