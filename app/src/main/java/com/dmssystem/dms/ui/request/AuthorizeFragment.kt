package com.dmssystem.dms.ui.request

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentAuthorizeBinding
import com.dmssystem.dms.ui.login.LoginFragmentDirections
import com.dmssystem.dms.util.Popup
import com.dmssystem.dms.util.lightStatusBar
import com.dmssystem.dms.util.setStatusBarColor
import com.dmssystem.dms.util.showToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton

class AuthorizeFragment : Fragment() {

    private lateinit var binding: FragmentAuthorizeBinding

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
        binding = FragmentAuthorizeBinding.inflate(inflater, container, false)

        setStatusBarColor(resources.getColor(R.color.white))
        initUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

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
                }
            }

            if (isDone) {

                pin = one1 + two2 + three3 + four4

                //verify the pin


                //show success popup or denial popup
                //showDenialPopup()
                showSuccessPopup()

            }

        }
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


    private fun showSuccessPopup() {

        val successPopup = Popup()
        successPopup.createPopup(requireContext(), R.layout.success_loan_qualification_layout)
        val goToHomeBtn = successPopup.view.findViewById<MaterialButton>(R.id.go_back_btn)

        goToHomeBtn.setOnClickListener {
            //navigate to dashboard
            navigateToDashboard()
            successPopup.dialog.dismiss()
        }
    }


    private fun showDenialPopup() {

        val denialPopup = Popup()
        denialPopup.createPopup(requireContext(), R.layout.denial_loan_qualification_layout)
        val topUpBtn = denialPopup.view.findViewById<MaterialButton>(R.id.top_up_btn)
        val goToHomeBtn = denialPopup.view.findViewById<MaterialButton>(R.id.go_back_home_btn)
        val backImg: ImageView = denialPopup.view.findViewById(R.id.arrow_back_denial)

        topUpBtn.setOnClickListener {

            showTopUpDialog()
        }

        goToHomeBtn.setOnClickListener {
            //navigate to dashboard
            navigateToDashboard()
            denialPopup.dialog.dismiss()
        }

        backImg.setOnClickListener {
            denialPopup.dialog.cancel()
        }
    }


    private fun showTopUpDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_top_up_layout)

        val continueBtn: MaterialButton = bottomSheetDialog.findViewById(R.id.action_topUp_sheet_btn)!!

        continueBtn.setOnClickListener {
            showToast("top up success")

        }

        bottomSheetDialog.show()
    }

    private fun navigateToDashboard() {

        val action = AuthorizeFragmentDirections.actionAuthorizeFragmentToDashboardFragment()
        findNavController().navigate(action)
    }
}