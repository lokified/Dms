package com.dmssystem.dms.util.dialogs

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.BottomSheetDialogWithdrawLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class WithdrawDialog(
private val amount: String,
private val contactNumber: String
): BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDialogWithdrawLayoutBinding
    private var isLayoutMyPhoneNumber = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BottomSheetDialogWithdrawLayoutBinding.inflate(inflater, container, false)

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

                withdrawBtn.setOnClickListener {

                    if (!isLayoutMyPhoneNumber) {
                        if (validatePhoneNumberInput()) {
                            dismiss()
                        }
                    }
                }

            }

            withdrawBtn.setOnClickListener {

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

            isLayoutMyPhoneNumber = false
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
            binding.lPhoneNumber.error = null

            isLayoutMyPhoneNumber = true
        }

    }

    private fun validatePhoneNumberInput(): Boolean {

        if (!isPhoneValid(binding.etPhoneNumberSheet.text!!)) {

            binding.lPhoneNumber.isErrorEnabled = true
            binding.lPhoneNumber.error = "Please enter a valid phone number"
            return false
        }

        return true
    }

    private fun isPhoneValid(text: Editable?): Boolean {

        return text != null && text.length == 9
    }
}