package com.dmssystem.dms.data.repository

import com.dmssystem.dms.data.remote.ApiService
import com.dmssystem.dms.data.remote.model.Pin
import com.dmssystem.dms.data.remote.model.response.OTP
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun getOTP() = api.getOTP()

    suspend fun confirmOTP(otp: OTP) = api.confirmOTP(otp)

    suspend fun updateNewPin(pin: Pin) = api.updateNewPin(pin)
}