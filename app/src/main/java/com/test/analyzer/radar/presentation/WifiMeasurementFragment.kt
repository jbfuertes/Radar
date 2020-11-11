package com.test.analyzer.radar.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Column
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import com.test.analyzer.radar.R
import com.test.analyzer.radar.databinding.FragmentWifiMeasurementsBinding
import com.test.analyzer.radar.presentation.common.base.BaseFragment

class WifiMeasurementFragment :
    BaseFragment<FragmentWifiMeasurementsBinding>(FragmentWifiMeasurementsBinding::inflate) {

    private val viewModel: WifiViewModel by navGraphViewModels(R.id.nav_main) {
        WifiViewModelFactory()
    }

    private lateinit var cartesian: Cartesian
    private lateinit var column: Column

    override fun setUpView() {
        super.setUpView()
        viewBinding.apply {
            cartesian = AnyChart.column()
            column = cartesian.column(listOf(ValueDataEntry("Test", (-40 + 90))))

            column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .offsetX(0)
                .offsetY(5)
                .format("function() { return this.value - 90}")

            cartesian.title("Wifi Signal Chart")
            cartesian.yScale().minimum(0)
            cartesian.yScale().maximum(70)

            cartesian.yAxis(0).labels().format("function() { return this.value - 90}")
            cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
            cartesian.interactivity().hoverMode(HoverMode.BY_X)

            cartesian.xAxis(0).title("UUID")
            cartesian.yAxis(0).title("SIGNAL POWER")

            barChart.setChart(cartesian)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.wifiData.observe(viewLifecycleOwner, Observer { list ->
            val columnList = list.map {
                ValueDataEntry(it.id.toString().take(5), it.signal + 90)
            }
            column.data(columnList)
            val top1 = list.getOrNull(0)?.let { getStringData(it) } ?: ""
            val top2 = list.getOrNull(1)?.let { getStringData(it) } ?: ""
            val top3 = list.getOrNull(2)?.let { getStringData(it) } ?: ""
            viewBinding.txtWifi.text = "$top1\n\n$top2\n\n$top3"
        })
    }
}