package com.dmssystem.dms.data.remote

import com.dmssystem.dms.data.local.model.User
import com.dmssystem.dms.data.remote.model.*
import com.dmssystem.dms.data.remote.model.response.LoginResponse
import com.dmssystem.dms.data.remote.model.response.UserResponse
import retrofit2.http.*

interface ApiService {

    @POST("user")
    suspend fun postUserDetails(
        @Body user: User
    ): UserResponse

    @GET("securityQuestions")
    suspend fun getSecurityQuestions(): List<Security>

    @POST("securityAnswer")
    suspend fun postSecurityAnswers(
        @Body securityAnswer: SecurityAnswer
    ): UserResponse

    @POST("sendOTP")
    suspend fun getOTP(@Body otp: Otp) : UserResponse

    @POST("verifyCode")
    suspend fun confirmOTP(
        @Body otp: OtpCode
    ): UserResponse

    @PATCH("user/{id}")
    suspend fun updateNewPin(
        @Body pin: Pin,
        @Path("id") userId: Int
    ): UserResponse

    @POST("accountLookup/{phoneNumber}")
    suspend fun accountLookUp(
        @Path("phoneNumber") phoneNumber: String
    ): UserResponse

    @POST("userLogin")
    suspend fun loginUser(
        @Body login: Login
    ): LoginResponse
}