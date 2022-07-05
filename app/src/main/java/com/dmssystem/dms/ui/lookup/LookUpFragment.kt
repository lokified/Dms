package com.dmssystem.dms.ui.lookup

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentLookUpBinding
import com.dmssystem.dms.util.VerifyPopup
import com.dmssystem.dms.util.lightStatusBar
import com.dmssystem.dms.util.setStatusBarColor

class LookUpFragment : Fragment() {
    private lateinit var binding: FragmentLookUpBinding

    private val popup = VerifyPopup()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLookUpBinding.inflate(inflater, container, false)

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

                    val action = LookUpFragmentDirections.actionLookUpFragmentToLoginFragment(null,false)
                    findNavController().navigate(action)

                }, 5000)

                popup.createVerifyPopup(context)
                popup.numberText.text = "We’ve sent a verification code to $phoneNumber"
                popup.timeCountdown.start()
            }

        }

        binding.lookupCancel.setOnClickListener {

            findNavController().navigateUp()
        }
    }

    private fun validatePhoneNumberInput(): Boolean {

        if (!isPhoneValid(binding.etPhoneNumber.text!!)) {
            binding.lPhoneNumber.isErrorEnabled = true
            binding.lPhoneNumber.error = "Please enter your phone number"
            return false
        }

        return true
    }

    private fun isPhoneValid(text: Editable?): Boolean {

        return text != null && text.length == 10
    }
}