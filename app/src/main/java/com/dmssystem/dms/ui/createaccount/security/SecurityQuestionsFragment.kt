package com.dmssystem.dms.ui.createaccount.security

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmssystem.dms.R
import com.dmssystem.dms.data.remote.model.Otp
import com.dmssystem.dms.data.remote.model.Security
import com.dmssystem.dms.data.remote.model.SecurityAnswer
import com.dmssystem.dms.databinding.FragmentSecurityQuestionsBinding
import com.dmssystem.dms.util.*
import com.dmssystem.dms.ui.dialogs.VerifyPopup
import com.dmssystem.dms.ui.otp.OTPViewModel
import com.dmssystem.dms.util.extensions.lightStatusBar
import com.dmssystem.dms.util.extensions.setStatusBarColor
import com.dmssystem.dms.util.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SecurityQuestionsFragment : Fragment() {

    private lateinit var binding: FragmentSecurityQuestionsBinding
    private val viewModel: SecurityViewModel by viewModels()
    private val otpViewModel: OTPViewModel by viewModels()
    private val args: SecurityQuestionsFragmentArgs by navArgs()
    private val popup = VerifyPopup()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecurityQuestionsBinding.inflate(inflater, container, false).apply {
            viewModel
            otpViewModel
        }

        setStatusBarColor(resources.getColor(R.color.white))

        setUpObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

        setUpAnswerEditText()

        binding.apply {

            continueBtn.setOnClickListener {

                val answers = SecurityAnswer(
                    etAnswerQ1.text.toString(),
                    etAnswerQ2.text.toString(),
                    etAnswerQ3.text.toString(),
                    args.userId
                )

                if (validateForm()) {

                    hideErrorMessage()
                    postAnswers(answers)
                }
            }

            backArrow.setOnClickListener {
                findNavController().navigateUp()
            }
        }

    }

    private fun setUpObservers() {

        viewModel.securityQuestions.observe(viewLifecycleOwner) {

            it?.let { questions ->

                questions[0].apply {

                    val question = listOf(
                        question_1,
                        question_2,
                        question_3
                    )
                    setUpQuestions(question)
                }
            }
        }

        viewModel.errorText.observe(viewLifecycleOwner, EventObserver {
            showToast(it)
        })
    }

    private fun postAnswers(answer: SecurityAnswer) {

        viewModel.postAnswers(answer).observe(viewLifecycleOwner) {

            binding.apply {

                it.let { resource ->

                    when(resource.status) {

                        Status.SUCCESS -> {

                            CoroutineScope(Dispatchers.Main).launch {

                                val otp = Otp(args.phoneNumber)
                                sendOTP(otp)
                                navigateToOtpFragment()
                                delay(5000L)
                                hidePopUp()
                            }
                        }

                        Status.ERROR -> {
                            showToast(resource.message!!)
                            hidePopUp()
                        }

                        Status.LOADING -> {

                            showPopUp()
                        }
                    }
                }
            }
        }
    }

    private fun sendOTP(otp: Otp) {

        otpViewModel.sendOtp(otp).observe(viewLifecycleOwner) {

            it.let { resource ->

                when(resource.status) {

                    Status.SUCCESS -> {

                        showToast(resource.data?.message!!)
                    }

                    Status.ERROR -> {
                        showToast(resource.message!!)

                    }

                    Status.LOADING -> {

                    }
                }
            }
        }
    }


    private fun navigateToOtpFragment() {

        val action = SecurityQuestionsFragmentDirections.actionSecurityQuestionsFragmentToOtpFragment(
            userName = args.userName, phoneNumber = args.phoneNumber, userId = args.userId)
        findNavController().navigate(action)
    }

    private fun showPopUp() {

        popup.createVerifyPopup(context)
        popup.numberText.text = "We’ve sent a verification code to ${args.phoneNumber}"
        popup.timeCountdown.start()
    }

    private fun hidePopUp() {

        popup.dialog.dismiss()
        popup.timeCountdown.cancel()
    }

    private fun setUpQuestions(questions: List<String>) {

        val adapter = ArrayAdapter(requireContext(), R.layout.list_security_question_layout, questions)
        binding.etQuestion1.setAdapter(adapter)
        binding.etQuestion2.setAdapter(adapter)
        binding.etQuestion3.setAdapter(adapter)
    }

    private fun setUpAnswerEditText() {

        binding.apply {

            etQuestion1.setOnClickListener { showAnswerEditText() }
            etQuestion2.setOnClickListener { showAnswerEditText() }
            etQuestion3.setOnDismissListener { showAnswerEditText() }
        }

    }


    private fun showAnswerEditText() {

        binding.apply {

            if(!etAnswerQ1.isVisible){
                etAnswerQ1.visibility = View.VISIBLE
            }

            else if(!etAnswerQ2.isVisible ) {
                etAnswerQ2.visibility = View.VISIBLE
            }

            else if(!etAnswerQ3.isVisible) {
                etAnswerQ3.visibility = View.VISIBLE
            }
        }

    }


    private fun validateForm(): Boolean {

        binding.apply {

            if (etAnswerQ1.text.isNullOrEmpty()) {
                etAnswerQ1.visibility = View.VISIBLE
                tvErrorMessage1.visibility = View.VISIBLE
                tvErrorMessage1.text = getString(R.string.input_answer)
                return false
            }
            if (etAnswerQ2.text.isNullOrEmpty()) {
                etAnswerQ2.visibility = View.VISIBLE
                tvErrorMessage2.visibility = View.VISIBLE
                tvErrorMessage2.text = getString(R.string.input_answer)
                return false
            }
            if (etAnswerQ3.text.isNullOrEmpty()) {
                etAnswerQ3.visibility = View.VISIBLE
                tvErrorMessage3.visibility = View.VISIBLE
                tvErrorMessage3.text = getString(R.string.input_answer)
                return false
            }

            return true
        }
    }

    private fun hideErrorMessage() {

        binding.apply {

            tvErrorMessage1.text = null
            tvErrorMessage2.text = null
            tvErrorMessage3.text = null
        }
    }
}