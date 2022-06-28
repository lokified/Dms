package com.dmssystem.dms.ui.request

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dmssystem.dms.databinding.LoanCompanyListItemLayoutBinding

class LoanCompaniesAdapter(
    private val companyList :List<CompanyItemModel>
): RecyclerView.Adapter<LoanCompaniesAdapter.LoanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LoanCompanyListItemLayoutBinding.inflate(inflater, parent, false)

        return LoanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {

        holder.bind(companyList[position])
    }

    override fun getItemCount(): Int = companyList.size

    inner class LoanViewHolder(val binding: LoanCompanyListItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {


        fun bind(companyItemModel: CompanyItemModel){

            binding.loanCompanyNameTxt.text = companyItemModel.name

            Glide.with(binding.loanImg).load(companyItemModel.image).into(binding.loanImg)
        }
    }
}