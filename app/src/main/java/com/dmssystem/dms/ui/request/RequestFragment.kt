package com.dmssystem.dms.ui.request

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentRequestBinding
import com.dmssystem.dms.ui.lookup.LookUpFragmentDirections
import com.dmssystem.dms.util.VerifyPopup
import com.dmssystem.dms.util.lightStatusBar
import com.dmssystem.dms.util.setStatusBarColor
import com.dmssystem.dms.util.showToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class RequestFragment : Fragment() {

    private lateinit var binding: FragmentRequestBinding
    private lateinit var companiesAdapter: LoanCompaniesAdapter

    private lateinit var requestBtn: MaterialButton
    private lateinit var idNumber: TextInputEditText

    private val popup = VerifyPopup()

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

            Handler().postDelayed(Runnable {

                popup.dialog.dismiss()
                popup.timeCountdown.cancel()

            }, 3000)

            popup.createVerifyPopup(context)
            popup.numberText.text = "We’ve sent a verification code to "
            popup.timeCountdown.start()

        }

        bottomSheetDialog.show()

    }
}