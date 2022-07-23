package com.dmssystem.dms.di

import android.app.Application
import androidx.room.Room
import com.dmssystem.dms.data.repository.userRepository.UserRepository
import com.dmssystem.dms.data.repository.userRepository.UserRepositoryImpl
import com.dmssystem.dms.data.local.database.UserDatabase
import com.dmssystem.dms.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://7a36-2c0f-fe38-2243-7f8e-9005-5a26-d0c6-412c.eu.ngrok.io/"

    private fun getOkHttpClient(): OkHttpClient.Builder =
        try{

            val builder = OkHttpClient.Builder()
            builder.addInterceptor {
                val request = it.request()
                    .newBuilder()
                    .apply {

                        addHeader("Authorization", "Bearer ")
                    }.build()

                it.proceed(request)

            }
            builder
        } catch(e: Exception) {
            throw RuntimeException(e)
        }

    @Provides
    @Singleton
    fun provideToDoDatabase(app: Application): UserDatabase {
        return Room.databaseBuilder(
            app,
            UserDatabase::class.java,
            "user_db"
        ) .build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(database: UserDatabase): UserRepository {

        return UserRepositoryImpl(database.userDao)
    }

    @Provides
    @Singleton
    fun provideRetrofitCall(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(retrofit: Retrofit): ApiService {

        return retrofit.create(ApiService::class.java)
    }
}