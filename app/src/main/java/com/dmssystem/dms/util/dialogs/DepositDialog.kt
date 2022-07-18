package com.dmssystem.dms.util.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.androidstudy.daraja.Daraja
import com.androidstudy.daraja.DarajaListener
import com.androidstudy.daraja.model.AccessToken
import com.androidstudy.daraja.model.LNMExpress
import com.androidstudy.daraja.model.LNMResult
import com.androidstudy.daraja.util.Env
import com.androidstudy.daraja.util.TransactionType
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.BottomSheetDialogDepositLayoutBinding
import com.dmssystem.dms.databinding.BottomSheetDialogRequestLayoutBinding
import com.dmssystem.dms.databinding.BottomSheetDialogWithdrawLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DepositDialog(
    private val amount: String,
    private val contactNumber: String
): BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDialogDepositLayoutBinding

    private lateinit var daraja: Daraja

    private var isLayoutMyNumber = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BottomSheetDialogDepositLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMpesa()


        binding.apply {
            etPhoneNumberSheet.setText(contactNumber)

            myNumberBtn.setOnClickListener {
                setMyNumberLayout()
            }

            otherNumberBtn.setOnClickListener {
                setOtherNumberLayout()
            }

            depositBtn.setOnClickListener {

                showProgressBar()

                if (!isLayoutMyNumber) {

                    val phoneNumber = "254${etPhoneNumberSheet.text.toString()}"
                    showStkPopup(phoneNumber, amount)
                }

                else {

                    val phoneNumber = "254${etPhoneNumberSheet.text.toString()}"
                    showStkPopup(phoneNumber, amount)
                }
            }
        }
    }

    private fun setOtherNumberLayout() {

        binding.apply {

            myNumberBtn.setTextColor(resources.getColor(R.color.sheetColor))
            myNumberBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.bottom_sheet_button)
            otherNumberBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.continue_btn_bg)

            otherNumberBtn.setTextColor(resources.getColor(R.color.white))

            etPhoneNumberSheet.setText("")
            etPhoneNumberSheet.isEnabled = true

            isLayoutMyNumber = false
        }

    }

    private fun setMyNumberLayout() {

        binding.apply {

            otherNumberBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.bottom_sheet_button)
            myNumberBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.continue_btn_bg)
            otherNumberBtn.setTextColor(resources.getColor(R.color.sheetColor))
            myNumberBtn.setTextColor(resources.getColor(R.color.white))

            etPhoneNumberSheet.setText(contactNumber)
            etPhoneNumberSheet.isEnabled = false

            isLayoutMyNumber = true
        }

    }

    private fun initMpesa() {

        daraja = Daraja.with(
            "WbAoIZdCLAX4sJAGqDGFvGOrp0yrqOeG",
            "o37aqvnfh5HT5H31",
            Env.SANDBOX,
            object : DarajaListener<AccessToken> {

                override fun onResult(accessToken: AccessToken) {

                    Log.i("access_token", accessToken.access_token)
                }

                override fun onError(error: String?) {

                    Log.i("token_error", error!!)
                }
            }
        )
    }

    private fun showStkPopup(phoneNumber: String, amount: String) {

        val lnmExpress = LNMExpress(
            "174379",
            "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
            TransactionType.CustomerPayBillOnline,  //transaction
            amount,  //amount
            phoneNumber,
            "174379",
            phoneNumber,   //phone number to use
            "https://mycallback.com",  //callbacks
            "001ABC",  //account number
            "Loan payment"  //description of the transaction
        )


        daraja.requestMPESAExpress(lnmExpress,

            object : DarajaListener<LNMResult> {
                override fun onResult(result: LNMResult) {

                    hideProgressBar()
                    Log.i("result_response", result.ResponseDescription)
                }

                override fun onError(error: String?) {

                    hideProgressBar()
                    Log.i("error_response", error!!)

                    Toast.makeText(
                        context,
                        "Error here, $error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun showProgressBar() {

        binding.apply {

            loading.visibility = View.VISIBLE
            depositBtn.text = ""
        }
    }

    private fun hideProgressBar() {
        binding.apply {

            loading.visibility = View.GONE
            depositBtn.text = "Deposit"

        }
    }
}