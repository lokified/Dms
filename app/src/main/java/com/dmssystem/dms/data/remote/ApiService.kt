package com.dmssystem.dms.data.remote

import com.dmssystem.dms.data.local.model.User
import com.dmssystem.dms.data.remote.model.Security
import com.dmssystem.dms.data.remote.model.SecurityAnswer
import com.dmssystem.dms.data.remote.model.response.LoginResponse
import com.dmssystem.dms.data.remote.model.response.OTP
import com.dmssystem.dms.data.remote.model.response.UserResponse
import retrofit2.http.*

interface ApiService {

    @POST("api/userDetails")
    suspend fun postUserDetails(
        @Body user: User
    ): UserResponse

    @GET("api/securityQuestions")
    suspend fun getSecurityQuestions(): List<Security>

    @POST("api/securityAnswers")
    suspend fun postSecurityAnswers(
        @Body securityAnswer: SecurityAnswer
    ): UserResponse

    @GET("api/getOTP")
    suspend fun getOTP() : OTP

    @GET("api/confirmOTP")
    suspend fun confirmOTP(
        @Body otp: OTP
    ): UserResponse

    @PATCH("api/updatePin")
    suspend fun updateNewPin(
        @Body pin: String
    ): UserResponse

    @POST("api/accountLookup")
    suspend fun accountLookUp(
        @Body phoneNumber: String
    ): UserResponse

    @POST("api/login")
    suspend fun loginUser(
        @Body pin: String
    ): LoginResponse
}