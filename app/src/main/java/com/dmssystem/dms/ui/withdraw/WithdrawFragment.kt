package com.dmssystem.dms.ui.withdraw

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentWithdrawBinding
import com.dmssystem.dms.util.lightStatusBar
import com.dmssystem.dms.util.setStatusBarColor
import com.google.android.material.bottomsheet.BottomSheetDialog

class WithdrawFragment : Fragment() {

    private lateinit var binding: FragmentWithdrawBinding


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

        binding.continueBtn.setOnClickListener {

            val amount = binding.etAmount.text.toString()

            Log.i("amount", amount)

            //show bottom sheet dialog
            showWithdrawalDialog()
        }


        binding.backArrow.setOnClickListener {

            findNavController().navigateUp()
        }
    }

    private fun showWithdrawalDialog() {

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_withdraw_layout)
        bottomSheetDialog.show()

    }

    private fun initUI() {

        binding.apply {
            binding.btnOne.setOnClickListener { controlPinPad1("1") }
            binding.btnTwo.setOnClickListener { controlPinPad1("2") }
            binding.btnThree.setOnClickListener { controlPinPad1("3") }
            binding.btnFour.setOnClickListener { controlPinPad1("4") }
            binding.btnFive.setOnClickListener { controlPinPad1("5") }
            binding.btnSix.setOnClickListener { controlPinPad1("6") }
            binding.btnSeven.setOnClickListener { controlPinPad1("7") }
            binding.btnEight.setOnClickListener { controlPinPad1("8") }
            binding.btnNine.setOnClickListener { controlPinPad1("9") }
            binding.btnZero.setOnClickListener { controlPinPad1("0") }
            binding.btnDelete.setOnClickListener { deletePinEntry() }
        }
    }

    private fun controlPinPad1(entry: String) {
        binding.apply {

            tvAmount.visibility = View.INVISIBLE
            etAmount.visibility = View.VISIBLE
            etAmount.append(entry)
        }
    }


    private fun deletePinEntry() {
        var display: String = binding.etAmount.text.toString()

        if(!TextUtils.isEmpty(display)) {

            display = display.substring(0, display.length - 1)

            binding.etAmount.setText(display)
        }
        else{

            binding.etAmount.visibility = View.INVISIBLE
            binding.tvAmount.visibility = View.VISIBLE
        }
    }
}