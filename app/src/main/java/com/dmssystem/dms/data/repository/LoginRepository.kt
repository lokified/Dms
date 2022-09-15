package com.dmssystem.dms.data.repository

import com.dmssystem.dms.data.remote.ApiService
import com.dmssystem.dms.data.remote.model.Login
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun accountLookUp(phoneNumber: String) = api.accountLookUp(phoneNumber)

    suspend fun loginUser(login: Login) = api.loginUser(login)
}