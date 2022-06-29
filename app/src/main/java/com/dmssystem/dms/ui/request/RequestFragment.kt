package com.dmssystem.dms.ui.request

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentRequestBinding
import com.dmssystem.dms.util.lightStatusBar
import com.dmssystem.dms.util.setStatusBarColor

class RequestFragment : Fragment() {

    private lateinit var binding: FragmentRequestBinding
    private lateinit var companiesAdapter: LoanCompaniesAdapter

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

        binding.backArrow.setOnClickListener {

            findNavController().navigateUp()
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
    }
}