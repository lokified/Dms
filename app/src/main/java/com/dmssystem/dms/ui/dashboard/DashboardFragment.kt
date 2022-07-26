package com.dmssystem.dms.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentDashboardBinding
import com.dmssystem.dms.util.extensions.lightStatusBar
import com.dmssystem.dms.util.extensions.setStatusBarColor

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)

        setStatusBarColor(resources.getColor(R.color.dashboardBgColor))
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

        binding.apply {

            withdrawLayout.setOnClickListener {

                val action = DashboardFragmentDirections.actionDashboardFragmentToWithdrawFragment()
                findNavController().navigate(action)
            }

            depositLayout.setOnClickListener {

                val action = DashboardFragmentDirections.actionDashboardFragmentToDepositFragment()
                findNavController().navigate(action)
            }

            requeatLoanLayout.setOnClickListener {

                val action = DashboardFragmentDirections.actionDashboardFragmentToRequestFragment()
                findNavController().navigate(action)
            }

            notificationImg.setOnClickListener {

                val action = DashboardFragmentDirections.actionDashboardFragmentToNotificationsFragment()
                findNavController().navigate(action)
            }
        }
    }

}