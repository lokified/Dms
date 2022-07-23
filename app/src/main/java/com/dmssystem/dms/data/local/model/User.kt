package com.dmssystem.dms.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val idNumber: String,
    val phoneNumber: String,
    val email: String
)
