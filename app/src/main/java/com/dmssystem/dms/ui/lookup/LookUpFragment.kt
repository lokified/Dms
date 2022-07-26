package com.dmssystem.dms.ui.lookup

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentLookUpBinding
import com.dmssystem.dms.util.Status
import com.dmssystem.dms.util.dialogs.VerifyPopup
import com.dmssystem.dms.util.extensions.lightStatusBar
import com.dmssystem.dms.util.extensions.setStatusBarColor
import com.dmssystem.dms.util.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LookUpFragment : Fragment() {
    private lateinit var binding: FragmentLookUpBinding
    private val viewModel : LookUpViewModel by viewModels()

    private val popup = VerifyPopup()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLookUpBinding.inflate(inflater, container, false).apply {
            viewModel
        }

        setStatusBarColor(resources.getColor(R.color.white))
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

        binding.etPhoneNumber.setOnKeyListener { _, _, _ ->

            if (isPhoneValid(binding.etPhoneNumber.text!!)){
                binding.lPhoneNumber.error = null
            }
            false
        }

        binding.lookupBtn.setOnClickListener {

            val phoneNumber = binding.etPhoneNumber.text.toString()

            if (validatePhoneNumberInput()) {

                binding.lPhoneNumber.helperText = null

                Handler().postDelayed(Runnable {

                    popup.dialog.dismiss()
                    popup.timeCountdown.cancel()

                    navigateToLogin()
                }, 5000)

                //accountLookUp(phoneNumber)
                showPopUp(phoneNumber)
            }

        }

        binding.lookupCancel.setOnClickListener {

            findNavController().navigateUp()
        }
    }

    private fun accountLookUp(phoneNumber: String) {

        viewModel.accountLookUp(phoneNumber).observe(viewLifecycleOwner) {

            it.let { resource ->

                when(resource.status) {

                    Status.SUCCESS -> {
                        dismissPopUp()
                        navigateToLogin()
                    }

                    Status.ERROR -> {

                        //show error
                        //dismiss popup
                        //navigate to create account
                        dismissPopUp()
                        showToast(resource.message!!)
                    }

                    Status.LOADING -> {

                        //show popup
                        showPopUp(phoneNumber)
                    }
                }
            }
        }
    }

    private fun showPopUp(phoneNumber: String) {

        popup.createVerifyPopup(context)
        popup.numberText.text = "We’ve sent a verification code to $phoneNumber"
        popup.timeCountdown.start()
    }

    private fun dismissPopUp() {
        popup.dialog.dismiss()
        popup.timeCountdown.cancel()
    }

    private fun navigateToLogin() {

        val action = LookUpFragmentDirections.actionLookUpFragmentToLandingFragment(true)
        findNavController().navigate(action)
    }

    private fun validatePhoneNumberInput(): Boolean {

        if (!isPhoneValid(binding.etPhoneNumber.text!!)) {
            binding.lPhoneNumber.isErrorEnabled = true
            binding.lPhoneNumber.error = "Please enter a valid phone number"
            return false
        }

        return true
    }

    private fun isPhoneValid(text: Editable?): Boolean {

        return text != null && text.length == 9
    }
}