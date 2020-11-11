package com.test.analyzer.radar.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.radar.RadarMeasurements
import com.test.analyzer.radar.data.RadarRepository
import com.test.analyzer.radar.di.ServiceLocator
import com.test.analyzer.radar.presentation.common.Scheduler
import com.test.analyzer.radar.presentation.common.base.BaseViewModel
import io.reactivex.rxjava3.kotlin.addTo

class PhoneMessagesViewModel(
    private val repository: RadarRepository,
    private val scheduler: Scheduler
): BaseViewModel() {

    private val _phoneMeasurements = MutableLiveData<RadarMeasurements>()
    val phoneMeasurements: LiveData<RadarMeasurements> get() = _phoneMeasurements

    private val _phoneMeasurementsOccurrence = MutableLiveData<List<Pair<String, Int>>>()
    val phoneMeasurementsOccurrence: LiveData<List<Pair<String, Int>>> get() = _phoneMeasurementsOccurrence

    init {
        observePhoneMeasurements()
        observeOccurrence()
    }

    private fun observePhoneMeasurements() {
        repository.phoneMeasurements()
            .subscribeOn(scheduler.io)
            .observeOn(scheduler.ui)
            .subscribe({
                _phoneMeasurements.value = it
            }, {
                it.printStackTrace()
            }).addTo(disposables)
    }

    private fun observeOccurrence() {
        repository.phoneMeasurementsOccurrence()
            .subscribeOn(scheduler.io)
            .observeOn(scheduler.ui)
            .subscribe({
                _phoneMeasurementsOccurrence.value = it
            }, {
                it.printStackTrace()
            }).addTo(disposables)
    }

    companion object {
        fun create(): PhoneMessagesViewModel {
            return PhoneMessagesViewModel(
                ServiceLocator.radarRepository,
                ServiceLocator.scheduler
            )
        }
    }

}