package com.dmssystem.dms.ui.createaccount.pin

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentPinBinding
import com.dmssystem.dms.util.SharedPreferenceManager
import com.dmssystem.dms.util.lightStatusBar
import com.dmssystem.dms.util.setStatusBarColor

class PinFragment : Fragment() {

    private lateinit var binding: FragmentPinBinding
    private val args: PinFragmentArgs by navArgs()

    private var pin = ""

    private var one1: String? = null
    private var two2: String? = null
    private var three3: String? = null
    private var four4: String? = null
    private var isDone = false

    private var mConfirmPin = ""
    private var isComplete = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPinBinding.inflate(inflater, container, false)

        setStatusBarColor(resources.getColor(R.color.white))
        initUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

        val userName = args.userName

        binding.tvUserName.text = userName

        binding.arrowBackImg.setOnClickListener {

            findNavController().navigateUp()
        }
    }

    private fun initUI() {

        binding.apply {
            btnOne.setOnClickListener { controlPinPad2("1") }
            btnTwo.setOnClickListener { controlPinPad2("2") }
            btnThree.setOnClickListener { controlPinPad2("3") }
            btnFour.setOnClickListener { controlPinPad2("4") }
            btnFive.setOnClickListener { controlPinPad2("5") }
            btnSix.setOnClickListener { controlPinPad2("6") }
            btnSeven.setOnClickListener { controlPinPad2("7") }
            btnEight.setOnClickListener { controlPinPad2("8") }
            btnNine.setOnClickListener { controlPinPad2("9") }
            btnZero.setOnClickListener { controlPinPad2("0") }
            btnDelete.setOnClickListener { deletePinEntry() }
        }
    }


    private fun controlPinPad2(entry: String) {
        binding.apply {
            when {
                one1 == null -> {
                    pin1.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.activestepsbackground
                        )
                    }
                    one1 = entry
                }
                two2 == null -> {
                    pin2.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.activestepsbackground
                        )
                    }
                    two2 = entry
                }
                three3 == null -> {
                    pin3.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.activestepsbackground
                        )
                    }
                    three3 = entry
                }
                four4 == null -> {
                    pin4.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.activestepsbackground
                        )
                    }
                    four4 = entry
                    isDone = true
                    isComplete = true
                }
            }

            if (isDone) {

                pin = one1 + two2 + three3 + four4

                SharedPreferenceManager.setInitialPin(context, pin)
                Log.i("pin_login", pin)

                changeToConfirmPinLayout()

                confirmUI()
            }

        }
    }

    private fun changeToConfirmPinLayout() {

        binding.create4digitpinTxt.text = "Confirm Pin"
        resetPin()

    }

    private fun verifyPin(confirmPin: String?){

        val shared = context?.getSharedPreferences("PinSharedPref", Context.MODE_PRIVATE)
        val initialPin = shared?.getString("initial_pin", "")


        if (initialPin == confirmPin) {

            navigateToLanding()
        }
    }


    private fun navigateToLanding() {

        val userName = args.userName

        val action = PinFragmentDirections.actionPinFragmentToLandingFragment(true, userName)
        findNavController().navigate(action)
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun deletePinEntry() {

        binding.apply {
            if (mConfirmPin != null && mConfirmPin!!.length > 0) {
                mConfirmPin = mConfirmPin!!.substring(0, mConfirmPin!!.length - 1)
            }
            if (four4 != null) {

                pin4.background = resources.getDrawable(R.drawable.inactive_pin_bg)
                four4 = null
                isDone = false
            }

            else if (three3 != null) {

                pin3.background = resources.getDrawable(R.drawable.inactive_pin_bg)
                three3 = null
            }

            else if (two2 != null) {

                pin2.background = resources.getDrawable(R.drawable.inactive_pin_bg)
                two2 = null
            }

            else if (one1 != null) {

                pin1.background = resources.getDrawable(R.drawable.inactive_pin_bg)
                one1 = null
            }
        }
    }

    private fun resetPin() {
        one1 = null
        two2 = null
        three3 = null
        four4 = null


        isDone = false

        binding.apply {

            pin4.background = resources.getDrawable(R.drawable.inactive_pin_bg)
            pin3.background = resources.getDrawable(R.drawable.inactive_pin_bg)
            pin2.background = resources.getDrawable(R.drawable.inactive_pin_bg)
            pin1.background = resources.getDrawable(R.drawable.inactive_pin_bg)
        }
    }


    private fun confirmUI() {

        binding.apply {
            btnOne.setOnClickListener { controlPinPad1("1") }
            btnTwo.setOnClickListener { controlPinPad1("2") }
            btnThree.setOnClickListener { controlPinPad1("3") }
            btnFour.setOnClickListener { controlPinPad1("4") }
            btnFive.setOnClickListener { controlPinPad1("5") }
            btnSix.setOnClickListener { controlPinPad1("6") }
            btnSeven.setOnClickListener { controlPinPad1("7") }
            btnEight.setOnClickListener { controlPinPad1("8") }
            btnNine.setOnClickListener { controlPinPad1("9") }
            btnZero.setOnClickListener { controlPinPad1("0") }
            btnDelete.setOnClickListener { deletePinEntry() }
        }
    }


    private fun controlPinPad1(entry: String) {
        binding.apply {
            when {
                one1 == null -> {
                    pin1.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.activestepsbackground
                        )
                    }
                    one1 = entry
                }
                two2 == null -> {
                    pin2.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.activestepsbackground
                        )
                    }
                    two2 = entry
                }
                three3 == null -> {
                    pin3.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.activestepsbackground
                        )
                    }
                    three3 = entry
                }
                four4 == null -> {
                    pin4.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.activestepsbackground
                        )
                    }
                    four4 = entry
                    isComplete = true
                }
            }

             if(isComplete) {

                mConfirmPin = one1 + two2 + three3 + four4
                Log.i("pin_con", mConfirmPin)

                verifyPin(mConfirmPin)

            }
        }
    }

}