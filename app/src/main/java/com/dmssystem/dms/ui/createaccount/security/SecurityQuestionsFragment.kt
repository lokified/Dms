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
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentSecurityQuestionsBinding
import com.dmssystem.dms.util.VerifyPopup

class SecurityQuestionsFragment : Fragment() {

    private lateinit var binding: FragmentSecurityQuestionsBinding
    private val args: SecurityQuestionsFragmentArgs by navArgs()
    private val popup = VerifyPopup()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecurityQuestionsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpQuestions()
        setUpAnswerEditText()

        val userName = args.userName
        val phoneNumber = args.phoneNumber

        binding.continueBtn.setOnClickListener {

            if (validateForm()) {

                hideErrorMessage()

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

        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
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
        (binding.lQuestion1.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (binding.lQuestion2.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (binding.lQuestion3.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun setUpAnswerEditText() {

        binding.etQuestion1.setOnClickListener { showAnswerEditText() }

        binding.etQuestion2.setOnClickListener { showAnswerEditText() }

        binding.etQuestion3.setOnDismissListener { showAnswerEditText() }
    }


    private fun showAnswerEditText() {

        if(!binding.etAnswerQ1.isVisible){
            binding.etAnswerQ1.visibility = View.VISIBLE
        }

        else if(!binding.etAnswerQ2.isVisible ) {
            binding.etAnswerQ2.visibility = View.VISIBLE
        }

        else if(!binding.etAnswerQ3.isVisible) {
            binding.etAnswerQ3.visibility = View.VISIBLE
        }
    }


    private fun validateForm(): Boolean {

        if (binding.etAnswerQ1.text.isNullOrEmpty()) {
            binding.etAnswerQ1.visibility = View.VISIBLE
            binding.tvErrorMessage1.visibility = View.VISIBLE
            binding.tvErrorMessage1.text = getString(R.string.input_answer)
            return false
        }
        if (binding.etAnswerQ2.text.isNullOrEmpty()) {
            binding.etAnswerQ2.visibility = View.VISIBLE
            binding.tvErrorMessage2.visibility = View.VISIBLE
            binding.tvErrorMessage2.text = getString(R.string.input_answer)
            return false
        }
        if (binding.etAnswerQ3.text.isNullOrEmpty()) {
            binding.etAnswerQ3.visibility = View.VISIBLE
            binding.tvErrorMessage3.visibility = View.VISIBLE
            binding.tvErrorMessage3.text = getString(R.string.input_answer)
            return false
        }

        return true
    }

    private fun hideErrorMessage() {

        binding.tvErrorMessage1.text = null
        binding.tvErrorMessage2.text = null
        binding.tvErrorMessage3.text = null
    }
}