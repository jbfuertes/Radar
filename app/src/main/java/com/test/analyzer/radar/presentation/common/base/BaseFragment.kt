package com.test.analyzer.radar.presentation.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.radar.Payload
import com.example.radar.PayloadType
import com.example.radar.RadarMeasurements
import com.test.analyzer.radar.R

abstract class BaseFragment<VB : ViewBinding>(
    private val setupBinding: (LayoutInflater) -> VB
) : Fragment() {

    lateinit var viewBinding: VB

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this@BaseFragment.handleOnBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = setupBinding(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        observeViewModel()
        loadInitialRequest()
    }

    open fun setUpView() {}
    open fun observeViewModel() {}
    open fun loadInitialRequest() {}

    open fun handleOnBackPressed() {
        if (findNavController().navigateUp().not()) {
            requireActivity().finish()
        }
    }

    fun getStringData(measurement: RadarMeasurements): String {
        val content = if (measurement.payloadType == PayloadType.PHONE_MESSAGE) {
            (measurement.payload as Payload.PhoneMessage).content
        } else {
            (measurement.payload as Payload.WifiSignal).content.toString()
        }
        return getString(
            R.string.card_content,
            measurement.id.toString(),
            measurement.signal.toString(),
            content
        )
    }

}