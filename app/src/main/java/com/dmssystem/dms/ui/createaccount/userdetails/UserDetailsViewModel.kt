package com.dmssystem.dms.ui.createaccount.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.dmssystem.dms.data.repository.userRepository.UserRepository
import com.dmssystem.dms.data.local.model.User
import com.dmssystem.dms.data.repository.RegisterRepository
import com.dmssystem.dms.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val registerRepository: RegisterRepository
) :ViewModel() {

    fun saveUserDetails(user: User) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {

            emit(Resource.success(registerRepository.postUserDetails(user)))
            userRepository.addUser(user)
        } catch (e: Exception) {

            emit(Resource.error(e.message ?: "Error occurred", data = null))
        }
    }
}