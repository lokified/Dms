package com.dmssystem.dms.ui.createaccount.userdetails

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dmssystem.dms.R
import com.dmssystem.dms.data.local.model.User
import com.dmssystem.dms.databinding.FragmentCreateAccountBinding
import com.dmssystem.dms.util.lightStatusBar
import com.dmssystem.dms.util.setStatusBarColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountFragment : Fragment() {

    private lateinit var binding: FragmentCreateAccountBinding
    private val viewModel: UserDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateAccountBinding.inflate(inflater, container, false).apply {
            viewModel
        }

        setStatusBarColor(resources.getColor(R.color.white))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lightStatusBar()

        binding.apply {

            continueBtn.setOnClickListener {

                val firstName = etFirstName.text.toString()
                val lastName = etLastName.text.toString()
                val phoneNumber = etPhoneNumber.text.toString()
                val email = etEmailAddress.text.toString()
                val idNumber = etIdNumber.text.toString()

                val user = User(
                    firstName = firstName,
                    lastName = lastName,
                    idNumber = idNumber,
                    phoneNumber = "254${phoneNumber}",
                    email = email
                )

                val userName = "$firstName $lastName"

                if (validateForm()) {

                    hideErrorMessage()
                    saveUserDetails(user)
                    navigateToSecurity(userName, phoneNumber)
                }
            }

            backArrow.setOnClickListener {

                findNavController().navigateUp()
            }
        }



    }

    private fun saveUserDetails(user: User) {

        viewModel.saveUserDetails(user)
    }


    private fun navigateToSecurity(userName: String, phoneNumber: String) {

        val action =
            CreateAccountFragmentDirections.actionCreateAccountFragmentToSecurityQuestionsFragment(userName, phoneNumber)
        findNavController().navigate(action)
    }


    private fun validateForm() : Boolean{

        binding.apply {

            if (etFirstName.text.isNullOrBlank()) {
                lFirstName.helperText = "Please enter first name"

                return false
            }

            if (etLastName.text.isNullOrBlank()) {
                lLastName.helperText = "Please enter last name"

                return false
            }

            if (etIdNumber.text.isNullOrBlank()) {
                lIdNumber.helperText = "Please enter id number"

                return false
            }

            if (etPhoneNumber.text.isNullOrEmpty()) {
                lPhoneNumber.helperText = "Please enter your phone number"

                return false
            }

            if (!validateEmail(etEmailAddress.text.toString())) {
                return false
            }

            return true
        }

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

        binding.apply {

            lFirstName.helperText = null
            lLastName.helperText = null
            lPhoneNumber.helperText = null
            lEmail.helperText = null
            lIdNumber.helperText = null
        }
    }

}