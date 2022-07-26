package com.dmssystem.dms.util.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.SuccessLoanQualificationLayoutBinding
import com.dmssystem.dms.util.extensions.lightStatusBar
import com.dmssystem.dms.util.extensions.setStatusBarColor

class SuccessPopupDialog: DialogFragment() {

    private lateinit var binding: SuccessLoanQualificationLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        setStatusBarColor(resources.getColor(R.color.white))
        binding = SuccessLoanQualificationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

        binding.apply {

            arrowBackSuccess.setOnClickListener {

                dismiss()
            }

            goBackBtn.setOnClickListener {

                navigateToDashboard()
                dismiss()
            }
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    private fun navigateToDashboard() {

        val action = SuccessPopupDialogDirections.actionSuccessPopupDialogToDashboardFragment()
        findNavController().navigate(action)
    }

}