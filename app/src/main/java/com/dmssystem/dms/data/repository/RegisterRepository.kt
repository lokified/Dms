package com.dmssystem.dms.data.repository

import com.dmssystem.dms.data.local.model.User
import com.dmssystem.dms.data.remote.ApiService
import com.dmssystem.dms.data.remote.model.Login
import com.dmssystem.dms.data.remote.model.Security
import com.dmssystem.dms.data.remote.model.SecurityAnswer
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun postUserDetails(user: User) = api.postUserDetails(user)

    suspend fun getSecurityQuestions(errorText:(String) -> Unit) : List<Security>? {

        return try {
            val securities = api.getSecurityQuestions()

            securities
        } catch (e: Exception) {

            errorText(e.toString())
            null
        }
    }

    suspend fun postSecurityAnswers(securityAnswer: SecurityAnswer) = api.postSecurityAnswers(securityAnswer)

    suspend fun loginUser(login: Login) = api.loginUser(login)
}