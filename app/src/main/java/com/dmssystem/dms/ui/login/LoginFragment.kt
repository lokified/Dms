package com.dmssystem.dms.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentLoginBinding
import com.dmssystem.dms.util.Status
import com.dmssystem.dms.util.extensions.lightStatusBar
import com.dmssystem.dms.util.extensions.setStatusBarColor
import com.dmssystem.dms.util.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val args: LoginFragmentArgs by navArgs()

    private val viewModel: LoginViewModel by viewModels()

    private var pin = ""

    private var one1: String? = null
    private var two2: String? = null
    private var three3: String? = null
    private var four4: String? = null
    private var isDone = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            viewModel
        }

        setStatusBarColor(resources.getColor(R.color.white))

        initUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

        binding.apply {

            if (args.islandingFragment) {
                tvUserName.text = args.userName
            }

            tvForgotPin.setOnClickListener {

                val action = LoginFragmentDirections.actionLoginFragmentToForgotPinFragment()
                findNavController().navigate(action)
            }

            arrowBackImg.setOnClickListener {

                findNavController().navigateUp()
            }
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
                }
            }

            if (isDone) {

                pin = one1 + two2 + three3 + four4

                navigateToDashboard()
                //loginUser(pin)
            }

        }
    }

    private fun loginUser(pin: String) {

        viewModel.loginUser(pin).observe(viewLifecycleOwner) {

            binding.apply {

                it.let { resource ->

                    when(resource.status) {

                        Status.SUCCESS -> {
                            navigateToDashboard()

                            val token = resource.data?.token

                            //save token
                        }

                        Status.ERROR -> {

                            showToast(resource.message ?: "something went wrong")
                        }

                        Status.LOADING -> {

                            //show loading
                        }
                    }
                }
            }
        }
    }

    private fun navigateToDashboard() {

        val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
        findNavController().navigate(action)
    }



    @SuppressLint("UseCompatLoadingForDrawables")
    private fun deletePinEntry() {

        binding.apply {

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
}