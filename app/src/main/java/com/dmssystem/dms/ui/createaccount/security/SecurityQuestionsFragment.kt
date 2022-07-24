package com.dmssystem.dms.ui.createaccount.security

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmssystem.dms.R
import com.dmssystem.dms.data.remote.model.Security
import com.dmssystem.dms.data.remote.model.SecurityAnswer
import com.dmssystem.dms.databinding.FragmentSecurityQuestionsBinding
import com.dmssystem.dms.util.*
import com.dmssystem.dms.util.dialogs.VerifyPopup

class SecurityQuestionsFragment : Fragment() {

    private lateinit var binding: FragmentSecurityQuestionsBinding
    private val viewModel: SecurityViewModel by viewModels()
    private val args: SecurityQuestionsFragmentArgs by navArgs()
    private val popup = VerifyPopup()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecurityQuestionsBinding.inflate(inflater, container, false).apply {
            viewModel
        }

        setStatusBarColor(resources.getColor(R.color.white))
        setUpQuestions()
        setUpObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

        setUpAnswerEditText()

        val userName = args.userName
        val phoneNumber = args.phoneNumber

        binding.apply {

            continueBtn.setOnClickListener {

                val answers = SecurityAnswer(
                    etAnswerQ1.text.toString(),
                    etAnswerQ2.text.toString(),
                    etAnswerQ3.text.toString()
                )

                if (validateForm()) {

                    hideErrorMessage()
                    //postAnswers(answers)

                    Handler().postDelayed( Runnable {

                        popup.dialog.dismiss()
                        popup.timeCountdown.cancel()

                        val action = SecurityQuestionsFragmentDirections.actionSecurityQuestionsFragmentToPinFragment(userName, phoneNumber)
                        findNavController().navigate(action)
                    }, 6000)

                    popup.createVerifyPopup(context)
                    popup.numberText.text = "We’ve sent a verification code to $phoneNumber"
                    popup.timeCountdown.start()
                }

            }

            backArrow.setOnClickListener {
                findNavController().navigateUp()
            }
        }

    }

    private fun setUpObservers() {

        viewModel.securityQuestions.observe(viewLifecycleOwner) {

            //setUpQuestions(it)
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

                            //get otp / navigate to otp screen
                        }

                        Status.ERROR -> {
                            showToast(resource.message!!)
                        }

                        Status.LOADING -> {

                            //show verify popup
                        }
                    }
                }
            }
        }
    }

    private fun setUpQuestions() {

        val questions = listOf(
            getString(R.string.favorite_color),
            getString(R.string.nickname),
            getString(R.string.favorite_pet),
            getString(R.string.favorite_month)
        )

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