package com.test.analyzer.radar.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.radar.RadarMeasurements
import com.test.analyzer.radar.data.RadarRepository
import com.test.analyzer.radar.di.ServiceLocator
import com.test.analyzer.radar.presentation.common.Scheduler
import com.test.analyzer.radar.presentation.common.base.BaseViewModel
import io.reactivex.rxjava3.kotlin.addTo

class WifiViewModel(
    private val repository: RadarRepository,
    private val scheduler: Scheduler
): BaseViewModel() {

    private val _wifiData = MutableLiveData<List<RadarMeasurements>>()
    val wifiData: LiveData<List<RadarMeasurements>> get() = _wifiData

    init {
        observePhoneMessages()
    }

    private fun observePhoneMessages() {
        repository.wifiSignal()
            .subscribeOn(scheduler.io)
            .observeOn(scheduler.ui)
            .subscribe({
                _wifiData.value = it
            }, {
                it.printStackTrace()
            }).addTo(disposables)
    }

    companion object {
        fun create(): WifiViewModel {
            return WifiViewModel(
                ServiceLocator.radarRepository,
                ServiceLocator.scheduler
            )
        }
    }

}