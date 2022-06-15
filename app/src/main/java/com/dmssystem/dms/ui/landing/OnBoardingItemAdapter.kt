package com.dmssystem.dms.ui.landing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dmssystem.dms.databinding.OnboardingItemLayoutBinding

class OnBoardingItemAdapter(
    private val onBoardingItems: List<OnBoardingItemModel>
) : RecyclerView.Adapter<OnBoardingItemAdapter.OnBoardingViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OnboardingItemLayoutBinding.inflate(inflater, parent, false)

        return OnBoardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

    inner class OnBoardingViewHolder(private val binding: OnboardingItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(onBoardingItems: OnBoardingItemModel) {

            binding.welcomeAnimation.setAnimation(onBoardingItems.onBoardingAnimation)
            binding.welcomeText.text = onBoardingItems.onBoardingText
        }
    }
}