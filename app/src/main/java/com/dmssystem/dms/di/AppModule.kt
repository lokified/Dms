package com.dmssystem.dms.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.dmssystem.dms.data.repository.userRepository.UserRepository
import com.dmssystem.dms.data.repository.userRepository.UserRepositoryImpl
import com.dmssystem.dms.data.local.database.UserDatabase
import com.dmssystem.dms.data.remote.ApiService
import com.dmssystem.dms.util.AuthInterceptor
import com.dmssystem.dms.util.DataStoreSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://dms-loan-api.herokuapp.com/api/"

    private fun getOkHttpClient(context: Context): OkHttpClient.Builder =
        try{

            val builder = OkHttpClient.Builder()
            builder.addInterceptor {

                AuthInterceptor(context).intercept(it)
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
    fun provideRetrofitCall(@ApplicationContext context: Context): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient(context).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(retrofit: Retrofit): ApiService {

        return retrofit.create(ApiService::class.java)
    }
}