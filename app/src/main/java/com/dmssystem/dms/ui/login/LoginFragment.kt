package com.dmssystem.dms.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private var pin = ""

    private var one1: String? = null
    private var two2: String? = null
    private var three3: String? = null
    private var four4: String? = null
    private var isDone = false

    private var mConfirmPin: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {

        binding.apply {
            binding.btnOne.setOnClickListener { controlPinPad2("1") }
            binding.btnTwo.setOnClickListener { controlPinPad2("2") }
            binding.btnThree.setOnClickListener { controlPinPad2("3") }
            binding.btnFour.setOnClickListener { controlPinPad2("4") }
            binding.btnFive.setOnClickListener { controlPinPad2("5") }
            binding.btnSix.setOnClickListener { controlPinPad2("6") }
            binding.btnSeven.setOnClickListener { controlPinPad2("7") }
            binding.btnEight.setOnClickListener { controlPinPad2("8") }
            binding.btnNine.setOnClickListener { controlPinPad2("9") }
            binding.btnZero.setOnClickListener { controlPinPad2("0") }
            binding.btnDelete.setOnClickListener { deletePinEntry() }
        }
    }

    private fun controlPinPad2(entry: String) {
        binding.apply {
            when {
                one1 == null -> {
                    binding.pin1.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.activestepsbackground
                        )
                    }
                    one1 = entry
                }
                two2 == null -> {
                    binding.pin2.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.activestepsbackground
                        )
                    }
                    two2 = entry
                }
                three3 == null -> {
                    binding.pin3.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.activestepsbackground
                        )
                    }
                    three3 = entry
                }
                four4 == null -> {
                    binding.pin4.background = context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.activestepsbackground
                        )
                    }
                    four4 = entry
                    isDone = true
                }
            }

            if (isDone) {

                pin = one1 + two2 + three3 + four4

            }

        }
    }



    @SuppressLint("UseCompatLoadingForDrawables")
    private fun deletePinEntry() {

        binding.apply {

            if (mConfirmPin != null && mConfirmPin!!.length > 0) {

                mConfirmPin = mConfirmPin!!.substring(0, mConfirmPin!!.length - 1)
            }
            if (four4 != null) {

                binding.pin4.background = resources.getDrawable(R.drawable.inactive_pin_bg)
                four4 = null
                isDone = false
            }

            if (three3 != null) {

                binding.pin3.background = resources.getDrawable(R.drawable.inactive_pin_bg)
                three3 = null
            }

            if (two2 != null) {

                binding.pin2.background = resources.getDrawable(R.drawable.inactive_pin_bg)
                two2 = null
            }

            if (one1 != null) {

                binding.pin1.background = resources.getDrawable(R.drawable.inactive_pin_bg)
                one1 = null
            }
        }
    }
}