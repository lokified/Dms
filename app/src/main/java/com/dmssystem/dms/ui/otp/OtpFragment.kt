package com.dmssystem.dms.ui.otp

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmssystem.dms.data.remote.model.OtpCode
import com.dmssystem.dms.databinding.FragmentOtpBinding
import com.dmssystem.dms.util.Status
import com.dmssystem.dms.util.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpFragment : Fragment() {

    private lateinit var binding: FragmentOtpBinding
    private val viewModel: OTPViewModel by viewModels()
    private val args: OtpFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(inflater, container, false).apply {
           viewModel
        }

        setListener()
        initFocus()
        return binding.root
    }


    private fun setListener(){

        binding.apply {
//
//            root.setOnClickListener{
//                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//                inputMethodManager.hideSoftInputFromWindow(root.windowToken, 0)
//            }
            setTextChangeListener(etValue1, etValue2)
            setTextChangeListener(etValue2, etValue3)
            setTextChangeListener(etValue3, etValue4)
            setTextChangeListener(etValue4, done = { verifyOTPCode() })

            setKeyListener(etValue2, etValue1)
            setKeyListener(etValue3, etValue2)
            setKeyListener(etValue4, etValue3)
        }

    }

    private fun verifyOTPCode() {

        binding.apply {

            val smsCode = etValue1.text.toString() + etValue2.text.toString() + etValue3.text.toString() + etValue4.text.toString()

            val otpCode = OtpCode(args.phoneNumber!!, smsCode)
            viewModel.confirmOtp(otpCode).observe(viewLifecycleOwner) {

                it.let { resource ->

                    when(resource.status) {

                        Status.SUCCESS -> {

                            progress.visibility = View.GONE

                            val success = "verification successful"
                            val alreadyVerified = "you are already verified"
                            val response = resource.data?.message!!

                            if (success == response) {

                                navigateToCreatePin()
                            }
                            else if(success == alreadyVerified) {

                                showToast(response)
                                //got to login
                            }
                            else {
                                showToast(response)
                                reset()
                            }
                        }

                        Status.ERROR -> {

                            progress.visibility = View.GONE
                            showToast(resource.message!!)
                            reset()
                        }

                        Status.LOADING -> {

                            progress.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun navigateToCreatePin() {

        val action = OtpFragmentDirections.actionOtpFragmentToPinFragment(args.userName, args.phoneNumber, args.userId)
        findNavController().navigate(action)
    }

    private fun reset(){

        binding.apply {

            etValue1.isEnabled = false
            etValue2.isEnabled = false
            etValue3.isEnabled = false
            etValue4.isEnabled = false
            etValue1.setText("")
            etValue2.setText("")
            etValue3.setText("")
            etValue4.setText("")
        }

        initFocus()
    }

    private fun initFocus(){
        binding.apply {

            etValue1.isEnabled = true
            etValue1.postDelayed({
                etValue1.requestFocus()
//                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//                inputMethodManager.showSoftInput(etValue1, InputMethodManager.SHOW_FORCED)
            }, 2000)
        }

    }


    private fun setTextChangeListener(
        fromEditText: EditText,
        targetEditText:EditText? = null,
        done:(()-> Unit)? = null
    ){
        fromEditText.addTextChangedListener {
            it?.let{ string ->
                if (string.isNotEmpty()){
                    targetEditText.let { editText ->
                        editText?.isEnabled = true
                        editText?.requestFocus()
                    }?: run{
                        done ?.let{done->
                            done()
                        }
                    }
                    fromEditText.clearFocus()
                    fromEditText.isEnabled = false
                }
            }
        }
    }

    private fun setKeyListener(fromEditText: EditText, backToEditText: EditText) {
        fromEditText.setOnKeyListener{ _, _, event ->
            if( null != event && KeyEvent.KEYCODE_DEL == event.keyCode){
                backToEditText.isEnabled = true
                backToEditText.requestFocus()
                backToEditText.setText("")
                backToEditText.clearFocus()
                backToEditText.isEnabled = false
            }
            false
        }
    }
}