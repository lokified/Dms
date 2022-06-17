package com.dmssystem.dms.ui.landing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentLandingBinding

class LandingFragment : Fragment() {

    private lateinit var binding: FragmentLandingBinding
    private lateinit var onBoardingItemAdapter: OnBoardingItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLandingBinding.inflate(layoutInflater, container, false)
        setOnBoardingItems()
        setUpIndicators()
        setCurrentIndicator(0)

        loadAnimation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newHereBtn.setOnClickListener {

            val action = LandingFragmentDirections.actionLandingFragmentToCreateAccountFragment()
            findNavController().navigate(action)
        }

        binding.loginTxt.setOnClickListener {
            val action = LandingFragmentDirections.actionLandingFragmentToLookUpFragment()
            findNavController().navigate(action)
        }
    }


    private fun loadAnimation() {

        val welcomeButtonAnimation = AnimationUtils.loadAnimation(context, R.anim.welcome_button_anim)

        binding.newHereBtn.startAnimation(welcomeButtonAnimation)
        binding.indicatorContainer.startAnimation(welcomeButtonAnimation)
    }

    private fun setOnBoardingItems() {

        onBoardingItemAdapter = OnBoardingItemAdapter(
            listOf(
                OnBoardingItemModel(
                    onBoardingAnimation = R.raw.welcome_animation,
                    onBoardingText = "Welcome to DMS \nWhere you maintain your \nCredit score"
                ),

                OnBoardingItemModel(
                    onBoardingAnimation = R.raw.loan_animation,
                    onBoardingText = "Borrow and let us pay \nYour debt when you are \nIn a fix"
                ),

                OnBoardingItemModel(
                    onBoardingAnimation = R.raw.welcome_3_animation,
                    onBoardingText = "Keep your loan limits \ngrowing by not \ndefaulting payments"
                )
            )
        )

        binding.viewPager.adapter = onBoardingItemAdapter
        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                setCurrentIndicator(position)
            }
        })
    }



    private fun setUpIndicators() {

        val indicators = arrayOfNulls<ImageView>(onBoardingItemAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

        layoutParams.setMargins(8,0,8,0)

        for (i in indicators.indices) {

            indicators[i] = ImageView(context)
            indicators[i]?.let {

                it.setImageDrawable(

                    ContextCompat.getDrawable(
                        context?.applicationContext!!,
                        R.drawable.tab_indicator_default
                    )
                )

                it.layoutParams = layoutParams
                binding.indicatorContainer.addView(it)
            }
        }
    }



    private fun setCurrentIndicator(position: Int) {

        val childCount = binding.indicatorContainer.childCount

        for (i in 0 until childCount) {

            val image = binding.indicatorContainer.getChildAt(i) as ImageView

            if (i == position) {

                image.setImageDrawable(
                    ContextCompat.getDrawable(
                        context?.applicationContext!!,
                        R.drawable.tab_indicator_selected
                    )
                )
            }
            else {

                image.setImageDrawable(
                    ContextCompat.getDrawable(
                        context?.applicationContext!!,
                        R.drawable.tab_indicator_default
                    )
                )
            }
        }
    }

}