package com.dmssystem.dms.ui.request

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.text.InputType
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat

class RequestFragment : Fragment() {

    private lateinit var binding: FragmentRequestBinding
    private lateinit var companiesAdapter: LoanCompaniesAdapter

    private lateinit var requestBtn: MaterialButton
    private lateinit var idNumber: TextInputEditText
    private lateinit var phoneNumber: TextInputEditText
    private lateinit var dateDue: TextInputEditText


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
        phoneNumber = bottomSheetDialog.findViewById(R.id.et_phone_number_request)!!
        dateDue = bottomSheetDialog.findViewById(R.id.et_due_date_request)!!


        dateDue.inputType = InputType.TYPE_NULL

        dateDue.setOnClickListener {
            dateDue.setOnKeyListener(null)
            showDatePicker()
        }

        requestBtn.setOnClickListener {

            bottomSheetDialog.dismiss()

            //checks for loan company
            val checkPopup = Popup()

            Handler().postDelayed(Runnable {

                checkPopup.dialog.dismiss()

                //navigate to loan details
                val action = RequestFragmentDirections.actionRequestFragmentToLoanDetailsFragment()
                findNavController().navigate(action)

            }, 3000)


            checkPopup.createPopup(requireContext(), R.layout.check_qualification_popup_layout)


        }

        bottomSheetDialog.show()

    }

    private fun showDatePicker() {

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Due date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val formattedDate = dateFormat.format(it)
            dateDue.setText(formattedDate)
        }

        datePicker.show(fragmentManager?.beginTransaction()!!, "Due Date")
    }
}