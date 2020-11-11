package com.test.analyzer.radar.presentation.model

import android.annotation.SuppressLint
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.test.analyzer.radar.R
import com.test.analyzer.radar.databinding.ItemPhoneMeasurementsBinding
import com.test.analyzer.radar.presentation.common.base.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class PhoneMeasurementsModel: ViewBindingEpoxyModelWithHolder<ItemPhoneMeasurementsBinding>() {

    @EpoxyAttribute
    lateinit var uuid: String

    @EpoxyAttribute
    var occurrence: Int = 0

    override fun getDefaultLayout() = R.layout.item_phone_measurements

    @SuppressLint("SetTextI18n")
    override fun ItemPhoneMeasurementsBinding.bind() {
        txtPhoneMeasurements.text = "$uuid : $occurrence"
    }
}