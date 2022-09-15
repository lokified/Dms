package com.dmssystem.dms.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.ConsentBottomSheetLayoutBinding
import com.dmssystem.dms.util.extensions.lightStatusBar
import com.dmssystem.dms.util.extensions.setStatusBarColor

class ConsentPopupDialog: DialogFragment() {

    private lateinit var binding: ConsentBottomSheetLayoutBinding

    private lateinit var onContinueListener: OnContinueListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        setStatusBarColor(resources.getColor(R.color.white))
        binding = ConsentBottomSheetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.ConsentDialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

        binding.apply {

            continueConsentBtn.setOnClickListener {

                if (consentCheckbox.isChecked) {

                    //navigate to authorize
                    onContinueListener.onClick(it)
                    dismiss()
                }
            }
        }
    }

    fun setOnButtonClickListener(clickListener: OnContinueListener)  {

        this.onContinueListener = clickListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    interface OnContinueListener {

        fun onClick(button: View)
    }
}