package com.dmssystem.dms.data.repository.userRepository

import com.dmssystem.dms.data.local.dao.UserDao
import com.dmssystem.dms.data.local.model.User

class UserRepositoryImpl(
    private val userDao: UserDao
): UserRepository {

    override suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    override suspend fun getUser(id: Int) {
        userDao.getUser(id)
    }
}