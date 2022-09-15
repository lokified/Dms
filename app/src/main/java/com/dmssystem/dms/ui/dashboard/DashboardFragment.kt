package com.dmssystem.dms.ui.dashboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.auth0.android.jwt.JWT
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentDashboardBinding
import com.dmssystem.dms.util.DataStoreSource
import com.dmssystem.dms.util.extensions.lightStatusBar
import com.dmssystem.dms.util.extensions.setStatusBarColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        CoroutineScope(Dispatchers.Main).launch {

            val token = DataStoreSource.getToken(requireContext())
            Log.i("access_token", token!!)

            val jwt = JWT(token)

            val firstName = jwt.getClaim("firstName").asString()
            val lastName = jwt.getClaim("lastName").asString()

            binding.userNameTxt.text = "$firstName $lastName"
        }

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