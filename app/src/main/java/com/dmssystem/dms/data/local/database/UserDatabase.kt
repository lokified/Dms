package com.dmssystem.dms.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dmssystem.dms.data.local.dao.UserDao
import com.dmssystem.dms.data.local.model.User

@Database( entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {

    abstract val userDao: UserDao
}