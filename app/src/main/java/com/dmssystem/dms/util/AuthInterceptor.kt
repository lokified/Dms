package com.dmssystem.dms.util

import android.content.Context
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val context: Context
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {

        val token = DataStoreSource.getToken(context)

        val request = chain.request()
            .newBuilder().apply {

            addHeader("Authorization", "Bearer $token")
        }.build()

        chain.proceed(request)
    }
}