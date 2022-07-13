package com.dmssystem.dms.ui.request

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentLoanDetailsBinding
import com.dmssystem.dms.util.lightStatusBar
import com.dmssystem.dms.util.setStatusBarColor
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton

class LoanDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLoanDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoanDetailsBinding.inflate(inflater, container, false)

        setStatusBarColor(resources.getColor(R.color.white))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

        binding.registerBtn.setOnClickListener {

            showConsentDetailPopUp()
        }

        binding.arrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    private fun showConsentDetailPopUp() {

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.consent_bottom_sheet_layout)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

        val continueBtn: MaterialButton = bottomSheetDialog.findViewById(R.id.continue_consent_btn)!!

        continueBtn.setOnClickListener {

            //navigate to authorize screen
            val action = LoanDetailsFragmentDirections.actionLoanDetailsFragmentToAuthorizeFragment()
            findNavController().navigate(action)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }
}