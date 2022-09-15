package com.dmssystem.dms.data.repository

import com.dmssystem.dms.data.remote.ApiService
import com.dmssystem.dms.data.remote.model.Otp
import com.dmssystem.dms.data.remote.model.OtpCode
import com.dmssystem.dms.data.remote.model.Pin
import com.dmssystem.dms.data.remote.model.response.OTP
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun getOTP(otp: Otp) = api.getOTP(otp)

    suspend fun confirmOTP(otpCode: OtpCode) = api.confirmOTP(otpCode)

    suspend fun updateNewPin(pin: Pin,  userId: Int) = api.updateNewPin(pin, userId)
}