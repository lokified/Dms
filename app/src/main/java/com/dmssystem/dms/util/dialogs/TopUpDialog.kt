package com.dmssystem.dms.util.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmssystem.dms.databinding.BottomSheetDialogTopUpLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TopUpDialog(
    private val amount: String,
): BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDialogTopUpLayoutBinding
    private lateinit var onContinueListener: TopUpDialog.OnContinueListener


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BottomSheetDialogTopUpLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {

            continueBtn.setOnClickListener {

                //show consent dialog
                onContinueListener.onClick(it)
                dismiss()
            }
        }
    }

    fun setOnContinueClickListener(clickListener: OnContinueListener)  {

        this.onContinueListener = clickListener
    }

    interface OnContinueListener {

        fun onClick(button: View)
    }

}