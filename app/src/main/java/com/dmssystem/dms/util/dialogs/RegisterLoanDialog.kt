package com.dmssystem.dms.util.dialogs

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmssystem.dms.databinding.BottomSheetDialogRequestLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat

class RegisterLoanDialog: BottomSheetDialogFragment() {

    private lateinit var onContinueListener: OnContinueListener

    private lateinit var binding: BottomSheetDialogRequestLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  {
        binding = BottomSheetDialogRequestLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            etDueDateRequest.inputType = InputType.TYPE_NULL

            etDueDateRequest.setOnClickListener {
                etDueDateRequest.setOnKeyListener(null)
                showDatePicker()
            }

            continueBtn.setOnClickListener {

                onContinueListener.onClick(it)
                dismiss()
            }
        }
    }

    fun setOnButtonClickListener(clickListener: OnContinueListener)  {

        this.onContinueListener = clickListener
    }

    private fun showDatePicker() {

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Due date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val formattedDate = dateFormat.format(it)
            binding.etDueDateRequest.setText(formattedDate)
        }

        datePicker.show(fragmentManager?.beginTransaction()!!, "Due Date")
    }

    interface OnContinueListener {

        fun onClick(button: View)
    }
}

