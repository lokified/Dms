package com.dmssystem.dms.ui.request

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentLoanDetailsBinding
import com.dmssystem.dms.util.dialogs.ConsentPopupDialog
import com.dmssystem.dms.util.dialogs.TopUpDialog
import com.dmssystem.dms.util.extensions.lightStatusBar
import com.dmssystem.dms.util.extensions.setStatusBarColor

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

        showUnsuccessfulLayout()

        binding.apply {

            registerBtn.setOnClickListener {

                showConsentDetailPopUp()
            }

            arrowBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }


    private fun showConsentDetailPopUp() {

        val popupDialog = ConsentPopupDialog()
        popupDialog.setOnButtonClickListener(onClickListener)
        popupDialog.show(parentFragmentManager, "Pop up")
    }

    private val onClickListener = object : ConsentPopupDialog.OnContinueListener {

        override fun onClick(button: View) {

            navigateToAuthorize()
        }
    }

    private fun showUnsuccessfulLayout() {

        binding.apply {

            registerBtn.visibility = View.GONE

            successTxt.text = "We are not able to settle your full loan. Kindly top up \nKSH 500 for us to settle your loan fully."
            topUpBtn.visibility = View.VISIBLE
            backHomeBtn.visibility = View.VISIBLE

            backHomeBtn.setOnClickListener {

               navigateToDashboard()
            }

            topUpBtn.setOnClickListener {
                showTopUpDialog("500")
            }
        }
    }

    private fun showTopUpDialog(amount: String) {

        val topUpBottomSheet = TopUpDialog(amount)
        topUpBottomSheet.setOnContinueClickListener(onContinueListener)
        topUpBottomSheet.show(parentFragmentManager, TopUpDialog.TAG)
    }

    private val onContinueListener = object : TopUpDialog.OnContinueListener {

        override fun onClick(button: View) {

            showConsentDetailPopUp()
        }
    }

    private fun navigateToDashboard() {

        val action = LoanDetailsFragmentDirections.actionLoanDetailsFragmentToDashboardFragment()
        findNavController().navigate(action)
    }

    private fun navigateToAuthorize() {

        val action = LoanDetailsFragmentDirections.actionLoanDetailsFragmentToAuthorizeFragment()
        findNavController().navigate(action)
    }
}