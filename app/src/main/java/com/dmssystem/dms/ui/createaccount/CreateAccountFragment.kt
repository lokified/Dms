package com.dmssystem.dms.ui.createaccount

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentCreateAccountBinding

class CreateAccountFragment : Fragment() {

    private lateinit var binding: FragmentCreateAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.continueBtn.setOnClickListener {

            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val phoneNumber = binding.etPhoneNumber.text.toString()

            val userName = "$firstName $lastName"

            if (validateForm()) {

                hideErrorMessage()

                val action =
                    CreateAccountFragmentDirections.actionCreateAccountFragmentToSecurityQuestionsFragment(userName, phoneNumber)
                findNavController().navigate(action)
            }
        }

        binding.backArrow.setOnClickListener {

            findNavController().navigateUp()
        }

    }


    private fun validateForm() : Boolean{

        if (binding.etFirstName.text.isNullOrBlank()) {
            binding.lFirstName.helperText = "Please enter first name"

            return false
        }

        if (binding.etLastName.text.isNullOrBlank()) {
            binding.lLastName.helperText = "Please enter last name"

            return false
        }

        if (binding.etIdNumber.text.isNullOrBlank()) {
            binding.lIdNumber.helperText = "Please enter id number"

            return false
        }

        if (binding.etPhoneNumber.text.isNullOrEmpty()) {
            binding.lPhoneNumber.helperText = "Please enter your phone number"

            return false
        }

        if (!validateEmail(binding.etEmailAddress.text.toString())) {
            return false
        }

        return true
    }

    private fun validateEmail(email: String): Boolean {
        if (email.isEmpty()) {
            binding.lEmail.helperText = "Please enter email"

            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.lEmail.helperText = "Please input valid email address"

            return false
        }

        return true
    }

    private fun hideErrorMessage() {

        binding.lFirstName.helperText = null
        binding.lLastName.helperText = null
        binding.lPhoneNumber.helperText = null
        binding.lEmail.helperText = null
        binding.lIdNumber.helperText = null
    }

}