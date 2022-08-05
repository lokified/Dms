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
import com.dmssystem.dms.databinding.FragmentAnotherMethodBinding
import com.dmssystem.dms.ui.dialogs.VerifyPopup
import com.dmssystem.dms.util.extensions.lightStatusBar
import com.dmssystem.dms.util.extensions.setStatusBarColor

class AnotherMethodFragment : Fragment() {

    private lateinit var binding: FragmentAnotherMethodBinding

    private val popup = VerifyPopup()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAnotherMethodBinding.inflate(layoutInflater, container, false)
        setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.white))
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()


        binding.arrowBackImg.setOnClickListener {

            findNavController().navigateUp()
        }

        binding.continueBtn.setOnClickListener {

            Handler().postDelayed(Runnable {

                popup.dialog.dismiss()
                popup.timeCountdown.cancel()

                val action = AnotherMethodFragmentDirections.actionAnotherMethodFragmentToPinFragment()
                findNavController().navigate(action)

            }, 6000)

            popup.createVerifyPopup(context)
            popup.numberText.text = "We’ve sent a verification code to +25472345678"
            popup.timeCountdown.start()
        }
    }
}