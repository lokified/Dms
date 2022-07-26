package com.dmssystem.dms.ui.deposit

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.navigation.fragment.findNavController
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentDepositBinding
import com.dmssystem.dms.util.dialogs.DepositDialog
import com.dmssystem.dms.util.extensions.lightStatusBar
import com.dmssystem.dms.util.extensions.setStatusBarColor

class DepositFragment : Fragment() {

    private lateinit var binding: FragmentDepositBinding


    private lateinit var inputConnection : InputConnection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDepositBinding.inflate(layoutInflater, container, false)

        setStatusBarColor(resources.getColor(R.color.white))
        initUI()
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

        binding.apply {

            etAmount.showSoftInputOnFocus = false


            continueBtn.setOnClickListener {

                if (validateAmount(etAmount.text)) {

                    withdrawWarningTxt.text = null
                    withdrawWarningTxt.visibility = View.INVISIBLE

                    val amount = etAmount.text.toString()

                    showDepositDialog(amount, "725992494")
                }

            }


            backArrow.setOnClickListener {

                findNavController().navigateUp()
            }
        }
    }

    private fun showDepositDialog(amount: String, contactNumber: String) {
        val modalBottomSheet = DepositDialog(amount, contactNumber)
        modalBottomSheet.show(parentFragmentManager, DepositDialog.TAG)

    }

    private fun validateAmount(text: Editable?): Boolean{

        binding.apply {

            val amount = etAmount.text.toString()

            if(text?.isEmpty()!!) {
                showEditText()
                withdrawWarningTxt.visibility = View.VISIBLE
                withdrawWarningTxt.text = "Enter an amount to continue"
                return false
            }

            else if (text.isNotEmpty()) {

                showEditText()
                val amountInt = amount.toInt()

                if (amountInt in 1..99 && !amount.startsWith("0")) {

                    withdrawWarningTxt.visibility = View.VISIBLE
                    withdrawWarningTxt.text = "Minimum amount is Kes 100"
                    return false
                }
                else if(amount.startsWith("0")){

                    for (i in amount.indices) {

                        if (amount[i].digitToInt() == 0) {

                            withdrawWarningTxt.visibility = View.VISIBLE
                            withdrawWarningTxt.text = "Enter a valid amount"
                            return false
                        }
                    }
                }
            }

            return true
        }
    }


    private fun initUI() {

        binding.apply {

            inputConnection = etAmount.onCreateInputConnection(EditorInfo())

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
            btnDelete.setOnClickListener { inputConnection.delete() }
        }
    }

    private fun controlPinPad1(entry: String) {
        binding.apply {

            showEditText()
            inputConnection.commitText(entry, 1)
        }
    }


    private fun InputConnection.delete() {
        if (getSelectedText(0).isNullOrBlank()) {
            deleteSurroundingText(1, 0)
        }
        else {
            commitText("", 1)
        }
    }

    private fun showEditText() {

        binding.apply {

            tvAmount.visibility = View.INVISIBLE
            etAmount.visibility = View.VISIBLE
        }
    }
}