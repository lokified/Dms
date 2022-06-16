package com.dmssystem.dms.ui.createaccount.security

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentSecurityQuestionsBinding

class SecurityQuestionsFragment : Fragment() {

    private lateinit var binding: FragmentSecurityQuestionsBinding
    private var isClicked: Boolean = false

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
    }

    private fun setUpQuestions() {

        val questions = listOf(
            getString(R.string.favorite_color),
            getString(R.string.nickname),
            getString(R.string.favorite_pet),
            getString(R.string.favorite_month)
        )

        val adapter = ArrayAdapter(requireContext(), R.layout.list_security_question_layout, questions)
        (binding.lQuestion2.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (binding.lQuestion3.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun setUpAnswerEditText() {

        binding.etQuestion1.setOnClickListener {

            toggleAnswerEditText1()
        }

        binding.etQuestion2.setOnClickListener { showAnswerEditText() }

        binding.etQuestion3.setOnDismissListener { showAnswerEditText() }
    }

    private fun toggleAnswerEditText1() {

        if(isClicked)  {
            hideAnswerEditText1()
        }
        else {
            showAnswerEditText1()
        }
    }

    private fun showAnswerEditText() {

        when{

            !binding.etAnswerQ2.isVisible -> {
                binding.etAnswerQ2.visibility = View.VISIBLE
                binding.etAnswerQ1.visibility = View.GONE
            }

            !binding.etAnswerQ3.isVisible -> {
                binding.etAnswerQ3.visibility = View.VISIBLE
                binding.etAnswerQ2.visibility = View.GONE
                binding.etAnswerQ1.visibility = View.GONE
            }
        }
    }

    private fun showAnswerEditText1() {

        if (!binding.etAnswerQ1.isVisible) {
            binding.etAnswerQ1.visibility = View.VISIBLE
        }
        isClicked = true

    }

    private fun hideAnswerEditText1() {

        if (binding.etAnswerQ1.isVisible) {
            binding.etAnswerQ1.visibility = View.GONE
        }

        isClicked = false
    }
}