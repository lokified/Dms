package com.dmssystem.dms.di

import android.app.Application
import androidx.room.Room
import com.dmssystem.dms.data.domain.repository.userRepository.UserRepository
import com.dmssystem.dms.data.domain.repository.userRepository.UserRepositoryImpl
import com.dmssystem.dms.data.local.database.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
}