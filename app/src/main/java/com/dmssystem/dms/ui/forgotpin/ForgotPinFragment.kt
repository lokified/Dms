package com.dmssystem.dms.ui.forgotpin

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentForgotPinBinding
import com.dmssystem.dms.util.dialogs.VerifyPopup
import com.dmssystem.dms.util.extensions.lightStatusBar
import com.dmssystem.dms.util.extensions.setStatusBarColor

class ForgotPinFragment : Fragment() {

    private lateinit var binding: FragmentForgotPinBinding

    private val popup = VerifyPopup()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgotPinBinding.inflate(layoutInflater, container, false)

        setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.white))
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

        binding.apply {

            arrowBackImg.setOnClickListener {

                findNavController().navigateUp()
            }


            anotherBtn.setOnClickListener {

                val action = ForgotPinFragmentDirections.actionForgotPinFragmentToAnotherMethodFragment()
                findNavController().navigate(action)
            }

            continueBtn.setOnClickListener {

                Handler().postDelayed(Runnable {

                    popup.dialog.dismiss()
                    popup.timeCountdown.cancel()

                    val action = ForgotPinFragmentDirections.actionForgotPinFragmentToPinFragment()
                    findNavController().navigate(action)

                }, 6000)

                popup.createVerifyPopup(context)
                popup.numberText.text = "We’ve sent a verification code to +25472345678"
                popup.timeCountdown.start()

            }
        }

    }
}