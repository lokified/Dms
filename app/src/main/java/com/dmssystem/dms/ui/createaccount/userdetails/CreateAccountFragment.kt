package com.dmssystem.dms.ui.createaccount.userdetails

import android.os.Bundle
import android.util.Log
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
import com.dmssystem.dms.util.EventObserver
import com.dmssystem.dms.util.Status
import com.dmssystem.dms.util.extensions.lightStatusBar
import com.dmssystem.dms.util.extensions.setStatusBarColor
import com.dmssystem.dms.util.extensions.showToast
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
                    phoneNumber = "+254${phoneNumber}",
                    email = email
                )

                if (validateForm()) {

                    hideErrorMessage()
                    saveUserDetails(user)
                }
            }

            backArrow.setOnClickListener {

                findNavController().navigateUp()
            }
        }



    }

    private fun saveUserDetails(user: User) {

        viewModel.saveUserDetails(user).observe(viewLifecycleOwner) {

            binding.apply {

                it.let { resource ->

                    when(resource.status) {

                        Status.SUCCESS -> {

                            progressBar.visibility = View.GONE
                            val userName = "${user.firstName} ${user.lastName}"

                            val userId = resource.data?.id
                            navigateToSecurity(userName, user.phoneNumber, userId!!)
                        }

                        Status.ERROR -> {

                            progressBar.visibility = View.GONE
                            showToast(resource.message!!)
                        }

                        Status.LOADING -> {

                            //show loading
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }


    private fun navigateToSecurity(userName: String, phoneNumber: String, userId: Int) {

        val action =
            CreateAccountFragmentDirections.actionCreateAccountFragmentToSecurityQuestionsFragment(userName, phoneNumber, userId)
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