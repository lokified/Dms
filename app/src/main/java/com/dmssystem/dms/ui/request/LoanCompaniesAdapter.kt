package com.dmssystem.dms.ui.request

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.LoanCompanyListItemLayoutBinding

class LoanCompaniesAdapter(
    private val companyList :List<CompanyItemModel>
): RecyclerView.Adapter<LoanCompaniesAdapter.LoanViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    var selectedCompany: CompoundButton? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LoanCompanyListItemLayoutBinding.inflate(inflater, parent, false)

        return LoanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {

        val company = companyList[position]
        holder.bind(company)

        holder.binding.loanCompany.setOnCheckedChangeListener { compoundButton, isChecked ->
            selectedCompany?.apply {
                setChecked(!isChecked)
            }
            selectedCompany = compoundButton.apply {
                setChecked(isChecked)
            }

        }

        holder.binding.loanCompany.setOnClickListener {

            if (holder.binding.loanCompany.isChecked) {
                selectedCompany = holder.binding.loanCompany
                holder.itemView.background = holder.itemView.context.resources.getDrawable(R.drawable.company_bg)

            }

            onItemClickListener.onItemClick(holder.itemView, position, company)
        }
    }

    override fun getItemCount(): Int = companyList.size


    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {

        this.onItemClickListener = itemClickListener
    }

    inner class LoanViewHolder(val binding: LoanCompanyListItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {


        fun bind(companyItemModel: CompanyItemModel){

            binding.loanCompany.text = companyItemModel.name

            Glide.with(binding.loanImg).load(companyItemModel.image).into(binding.loanImg)
        }
    }

    interface OnItemClickListener {

        fun onItemClick(view: View, position: Int, company: CompanyItemModel)
    }
}