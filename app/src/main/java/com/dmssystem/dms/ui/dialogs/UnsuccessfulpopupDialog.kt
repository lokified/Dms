package com.dmssystem.dms.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.DenialLoanQualificationLayoutBinding
import com.dmssystem.dms.util.extensions.lightStatusBar
import com.dmssystem.dms.util.extensions.setStatusBarColor

class UnsuccessfulpopupDialog: DialogFragment() {


    private lateinit var binding: DenialLoanQualificationLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        setStatusBarColor(resources.getColor(R.color.white))
        binding = DenialLoanQualificationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

        binding.apply {

            arrowBackDenial.setOnClickListener {

                dismiss()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }


}