package com.dmssystem.dms.ui.createaccount.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmssystem.dms.data.domain.repository.userRepository.UserRepository
import com.dmssystem.dms.data.local.dao.UserDao
import com.dmssystem.dms.data.local.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userRepository: UserRepository
) :ViewModel() {

    fun saveUserDetails(user: User) {

        viewModelScope.launch {

            userRepository.addUser(user)
        }
    }
}