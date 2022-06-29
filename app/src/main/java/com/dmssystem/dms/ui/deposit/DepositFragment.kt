package com.dmssystem.dms.ui.deposit

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentDepositBinding
import com.dmssystem.dms.util.lightStatusBar
import com.dmssystem.dms.util.setStatusBarColor
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class DepositFragment : Fragment() {

    private lateinit var binding: FragmentDepositBinding

    private lateinit var myNumberBtn: MaterialButton
    private lateinit var otherNumberBtn: MaterialButton
    private lateinit var phoneEditText: TextInputEditText

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

        binding.etAmount.showSoftInputOnFocus = false

        binding.continueBtn.setOnClickListener {

            val amount = binding.etAmount.text.toString()

            showDepositDialog()
            Log.i("amount", amount)

        }


        binding.backArrow.setOnClickListener {

            findNavController().navigateUp()
        }
    }

    private fun showDepositDialog() {

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_withdraw_layout)

        myNumberBtn = bottomSheetDialog.findViewById(R.id.myNumber_btn)!!
        otherNumberBtn = bottomSheetDialog.findViewById(R.id.other_number_btn)!!
        phoneEditText = bottomSheetDialog.findViewById(R.id.et_phone_number_sheet)!!
        val depositBtn: MaterialButton = bottomSheetDialog.findViewById(R.id.action_sheet_btn)!!
        val actionToText: TextView = bottomSheetDialog.findViewById(R.id.action_sheet_to_txt)!!

        depositBtn.text = "Deposit"
        actionToText.text = "Deposit From"
        phoneEditText.setText("0725992494")
        phoneEditText.isEnabled = false

        myNumberBtn.setOnClickListener {

            setMyNumberLayout()
        }
        otherNumberBtn.setOnClickListener {

            setOtherNumberLayout()

        }

        bottomSheetDialog.show()

    }


    private fun setOtherNumberLayout() {
        myNumberBtn.setTextColor(resources.getColor(R.color.sheetColor))
        myNumberBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.bottom_sheet_button)
        otherNumberBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.continue_btn_bg)

        otherNumberBtn.setTextColor(resources.getColor(R.color.white))

        phoneEditText.setText("")
        phoneEditText.isEnabled = true
    }

    private fun setMyNumberLayout() {

        otherNumberBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.bottom_sheet_button)
        myNumberBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.continue_btn_bg)
        otherNumberBtn.setTextColor(resources.getColor(R.color.sheetColor))
        myNumberBtn.setTextColor(resources.getColor(R.color.white))

        phoneEditText.setText("0725992494")
        phoneEditText.isEnabled = false
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