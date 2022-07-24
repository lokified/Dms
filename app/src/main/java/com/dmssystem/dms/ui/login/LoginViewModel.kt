package com.dmssystem.dms.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dmssystem.dms.data.repository.RegisterRepository
import com.dmssystem.dms.util.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    fun loginUser(pin: String) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {

            emit(Resource.success(data = registerRepository.loginUser(pin)))
        } catch (e: Exception) {

            emit(Resource.error(message = e.message ?: "Error occurred", data = null))
        }
    }
}