package com.dmssystem.dms.ui.withdraw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.navigation.fragment.findNavController
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentWithdrawBinding
import com.dmssystem.dms.util.dialogs.WithdrawDialog
import com.dmssystem.dms.util.lightStatusBar
import com.dmssystem.dms.util.setStatusBarColor

class WithdrawFragment : Fragment() {

    private lateinit var binding: FragmentWithdrawBinding

    private lateinit var inputConnection : InputConnection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWithdrawBinding.inflate(layoutInflater, container, false)

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

                val amount = binding.etAmount.text.toString()

                //show bottom sheet dialog
                showWithdrawalDialog(amount, "0712345678")
            }


            backArrow.setOnClickListener {

                findNavController().navigateUp()
            }
        }
    }

    private fun showWithdrawalDialog(amount: String, contactNumber: String) {

        val modalBottomSheet = WithdrawDialog(amount, contactNumber)
        modalBottomSheet.show(parentFragmentManager, WithdrawDialog.TAG)
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

            tvAmount.visibility = View.INVISIBLE
            etAmount.visibility = View.VISIBLE
            etAmount.append(entry)
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
}