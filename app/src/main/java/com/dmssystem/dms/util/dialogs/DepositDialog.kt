package com.dmssystem.dms.util.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.BottomSheetDialogDepositLayoutBinding
import com.dmssystem.dms.databinding.BottomSheetDialogRequestLayoutBinding
import com.dmssystem.dms.databinding.BottomSheetDialogWithdrawLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DepositDialog(
    private val amount: String,
    private val contactNumber: String
): BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDialogDepositLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BottomSheetDialogDepositLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            etPhoneNumberSheet.setText(contactNumber)

            myNumberBtn.setOnClickListener {
                setMyNumberLayout()
            }

            otherNumberBtn.setOnClickListener {
                setOtherNumberLayout()
            }

            depositBtn.setOnClickListener {

                dismiss()
            }
        }
    }

    private fun setOtherNumberLayout() {

        binding.apply {

            myNumberBtn.setTextColor(resources.getColor(R.color.sheetColor))
            myNumberBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.bottom_sheet_button)
            otherNumberBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.continue_btn_bg)

            otherNumberBtn.setTextColor(resources.getColor(R.color.white))

            etPhoneNumberSheet.setText("")
            etPhoneNumberSheet.isEnabled = true
        }

    }

    private fun setMyNumberLayout() {

        binding.apply {

            otherNumberBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.bottom_sheet_button)
            myNumberBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.continue_btn_bg)
            otherNumberBtn.setTextColor(resources.getColor(R.color.sheetColor))
            myNumberBtn.setTextColor(resources.getColor(R.color.white))

            etPhoneNumberSheet.setText(contactNumber)
            etPhoneNumberSheet.isEnabled = false
        }

    }
}