package com.dmssystem.dms.ui.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentNotificationsBinding
import com.dmssystem.dms.util.extensions.lightStatusBar
import com.dmssystem.dms.util.extensions.setStatusBarColor

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var adapter: NotificationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationsBinding.inflate(layoutInflater, container, false)

        setStatusBarColor(resources.getColor(R.color.white))
        setUpNotifications()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

        binding.backArrow.setOnClickListener {

            findNavController().navigateUp()
        }
    }


    private fun setUpNotifications() {

        adapter = NotificationsAdapter(
            listOf(
                NotificationsModel(
                    getString(R.string.withdrawal_failed),
                    getString(R.string._10_32pm),
                    "Your withdrawal of KSH 500 failed due to insufficient funds Available in your wallet",
                    R.drawable.ic_notification_failed
                ),

                NotificationsModel(
                    getString(R.string.deposit_successful),
                    getString(R.string._10_32pm),
                    "Your deposit of KSH 500 was successful your available Wallet balance is KSH 500.1",
                    R.drawable.ic_notification_success
                ),

                NotificationsModel(
                    getString(R.string.request_loan_payment_successful),
                    getString(R.string._10_32pm),
                    "Your loan from Tala of KSH 500 was successfully paid and Your new loan was approved for KSH 550 for a duration 30 days",
                    R.drawable.ic_notification_success
                ),

                NotificationsModel(
                    getString(R.string.request_loan_payment_failed),
                    getString(R.string._10_32pm),
                    "Your loan from Tala of KSH 500 was unsuccessful due to Your low loan limit with Tala",
                    R.drawable.ic_notification_failed
                )
            )
        )

        binding.notificationRecycler.layoutManager = LinearLayoutManager(context)
        binding.notificationRecycler.adapter = adapter
    }
}