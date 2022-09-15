package com.dmssystem.dms.data.repository.userRepository

import com.dmssystem.dms.data.local.model.User

interface UserRepository {

    suspend fun addUser(user: User)
    suspend fun getUser(id: Int)
}