package com.dmssystem.dms.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dmssystem.dms.data.remote.model.Login
import com.dmssystem.dms.data.repository.LoginRepository
import com.dmssystem.dms.data.repository.RegisterRepository
import com.dmssystem.dms.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    fun loginUser(login: Login) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {

            emit(Resource.success(data = loginRepository.loginUser(login)))
        } catch (e: Exception) {

            emit(Resource.error(message = e.message ?: "Error occurred", data = null))
        }
    }
}