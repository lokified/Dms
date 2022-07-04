package com.dmssystem.dms.ui.request

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentRequestBinding
import com.dmssystem.dms.ui.lookup.LookUpFragmentDirections
import com.dmssystem.dms.util.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class RequestFragment : Fragment() {

    private lateinit var binding: FragmentRequestBinding
    private lateinit var companiesAdapter: LoanCompaniesAdapter

    private lateinit var requestBtn: MaterialButton
    private lateinit var idNumber: TextInputEditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRequestBinding.inflate(layoutInflater, container, false)

        setStatusBarColor(resources.getColor(R.color.white))

        setUpLoanCompanies()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

        binding.apply {

            backArrow.setOnClickListener {

                findNavController().navigateUp()
            }


            continueBtn.setOnClickListener {
                showIdCheckDialog()
            }
        }

    }

    private fun setUpLoanCompanies() {

        companiesAdapter = LoanCompaniesAdapter(
            listOf(
                CompanyItemModel("Timiza", R.drawable.timiza),
                CompanyItemModel("Mwananchi Credit", R.drawable.mwananchi),
                CompanyItemModel("Zash Loan", R.drawable.zash),
                CompanyItemModel("Branch Loan", R.drawable.branch),
                CompanyItemModel("Helacash - personal loan", R.drawable.hf)
            )
        )

        binding.loanCompanyRecycler.layoutManager = LinearLayoutManager(context)
        binding.loanCompanyRecycler.adapter = companiesAdapter
        companiesAdapter.setOnItemClickListener(onItemClickListener)
    }

    private val onItemClickListener = object : LoanCompaniesAdapter.OnItemClickListener {

        override fun onItemClick(view: View, position: Int, company: CompanyItemModel) {
            showToast("${company.name} selected")
        }
    }


    private fun showIdCheckDialog() {

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_request_layout)

        requestBtn = bottomSheetDialog.findViewById(R.id.action_sheet_btn)!!
        idNumber = bottomSheetDialog.findViewById(R.id.et_id_number_request)!!


        requestBtn.setOnClickListener {

            bottomSheetDialog.dismiss()

            val checkPopup = Popup()

            Handler().postDelayed(Runnable {

                checkPopup.dialog.dismiss()

                //showSuccessPopup()
                showDenialPopup()

            }, 3000)


            checkPopup.createPopup(requireContext(), R.layout.check_qualification_popup_layout)


        }

        bottomSheetDialog.show()

    }


    private fun showSuccessPopup() {

        val successPopup = Popup()
        successPopup.createPopup(requireContext(), R.layout.success_loan_qualification_layout)
        val goToHomeBtn = successPopup.view.findViewById<MaterialButton>(R.id.go_back_btn)

        goToHomeBtn.setOnClickListener {
            successPopup.dialog.cancel()
        }
    }


    private fun showDenialPopup() {

        val denialPopup = Popup()
        denialPopup.createPopup(requireContext(), R.layout.denial_loan_qualification_layout)
        val topUpBtn = denialPopup.view.findViewById<MaterialButton>(R.id.top_up_btn)
        val goToHomeBtn = denialPopup.view.findViewById<MaterialButton>(R.id.go_back_home_btn)
        val backImg: ImageView = denialPopup.view.findViewById(R.id.arrow_back_denial)

        topUpBtn.setOnClickListener {

            showTopUpDialog()
        }

        goToHomeBtn.setOnClickListener {
            denialPopup.dialog.cancel()
        }

        backImg.setOnClickListener {
            denialPopup.dialog.cancel()
        }
    }


    private fun showTopUpDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_top_up_layout)

        val continueBtn: MaterialButton = bottomSheetDialog.findViewById(R.id.action_topUp_sheet_btn)!!

        continueBtn.setOnClickListener {
            showToast("top up success")

        }

        bottomSheetDialog.show()
    }
}