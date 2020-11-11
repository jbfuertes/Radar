package com.test.analyzer.radar.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tbruyelle.rxpermissions3.RxPermissions
import com.test.analyzer.radar.R
import com.test.analyzer.radar.databinding.FragmentDashboardBinding
import com.test.analyzer.radar.presentation.common.base.BaseFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class DashboardFragment :
    BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {

    private val messagesViewModel: PhoneMessagesViewModel by navGraphViewModels(R.id.nav_main) {
        PhoneMessagesViewModelFactory()
    }
    private val wifiViewModel: WifiViewModel by navGraphViewModels(R.id.nav_main) {
        WifiViewModelFactory()
    }
    private lateinit var disposables: CompositeDisposable

    override fun setUpView() {
        super.setUpView()
        disposables = CompositeDisposable()
        requestPermission()
        viewBinding.apply {
            crdPhoneMessages.setOnClickListener {
                findNavController().navigate(R.id.action_to_fragment_phone_messages)
            }

            crdWifiSignal.setOnClickListener {
                findNavController().navigate(R.id.action_to_fragment_wifi_measurements)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun observeViewModel() {
        super.observeViewModel()
        messagesViewModel.phoneMeasurements.observe(viewLifecycleOwner, Observer {
            viewBinding.txtMessage.text = getStringData(it)
        })

        wifiViewModel.wifiData.observe(viewLifecycleOwner, Observer { list ->
            val top1 = list.getOrNull(0)?.let { getStringData(it) } ?: ""
            val top2 = list.getOrNull(1)?.let { getStringData(it) } ?: ""
            val top3 = list.getOrNull(2)?.let { getStringData(it) } ?: ""
            viewBinding.txtWifi.text = "$top1\n\n$top2\n\n$top3"
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    private fun requestPermission() {
        RxPermissions(this)
            .requestEachCombined(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .subscribe({
                if (it.granted) {
                    val wifiManager =
                        requireActivity().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                    viewBinding.txtSsid.text = wifiManager.connectionInfo.ssid
                } else {
                    Toast.makeText(
                        requireContext(),
                        "App needs this permission to detect ssid",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }, {
                it.printStackTrace()
            }).addTo(disposables)
    }

}