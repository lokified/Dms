package com.dmssystem.dms.ui.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dmssystem.dms.data.remote.model.Login
import com.dmssystem.dms.data.remote.model.Otp
import com.dmssystem.dms.data.remote.model.OtpCode
import com.dmssystem.dms.data.repository.MainRepository
import com.dmssystem.dms.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class OTPViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {

    fun sendOtp(otp: Otp) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {

            emit(Resource.success(data = mainRepository.getOTP(otp)))
        } catch (e: Exception) {

            emit(Resource.error(message = e.message ?: "Error occurred", data = null))
        }
    }


    fun confirmOtp(otpCode: OtpCode) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {

            emit(Resource.success(data = mainRepository.confirmOTP(otpCode)))
        } catch (e: Exception) {

            emit(Resource.error(message = e.message ?: "Error occurred", data = null))
        }
    }
}