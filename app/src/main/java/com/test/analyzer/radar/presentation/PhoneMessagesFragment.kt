package com.test.analyzer.radar.presentation

import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.test.analyzer.radar.R
import com.test.analyzer.radar.databinding.FragmentPhoneMessagesBinding
import com.test.analyzer.radar.presentation.common.base.BaseFragment
import com.test.analyzer.radar.presentation.controller.PhoneMessagesController

class PhoneMessagesFragment :
    BaseFragment<FragmentPhoneMessagesBinding>(FragmentPhoneMessagesBinding::inflate) {

    private val viewModel: PhoneMessagesViewModel by navGraphViewModels(R.id.nav_main) {
        PhoneMessagesViewModelFactory()
    }

    private val controller = PhoneMessagesController()

    override fun setUpView() {
        super.setUpView()
        viewBinding.rcvPhoneMessages.setController(controller)
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.phoneMeasurements.observe(viewLifecycleOwner, Observer {
            viewBinding.txtMessage.text = getStringData(it)
        })

        viewModel.phoneMeasurementsOccurrence.observe(viewLifecycleOwner, Observer {
            controller.setPhoneMeasurements(it)
        })
    }
}