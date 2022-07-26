package com.dmssystem.dms.ui.lookup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dmssystem.dms.data.repository.LoginRepository
import com.dmssystem.dms.data.repository.RegisterRepository
import com.dmssystem.dms.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LookUpViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {

    fun accountLookUp(phoneNumber: String) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {

            emit(Resource.success(data = loginRepository.accountLookUp(phoneNumber)))
        } catch (e: Exception) {

            emit(Resource.error(e.message ?: "Error occurred", data = null))
        }
    }
}