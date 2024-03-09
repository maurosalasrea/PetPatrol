package com.example.petpatrol.api

import java.util.Date

data class Users(
    val user_id: Int,
    val email_address: String,
    val password: String,
    val first_name: String,
    val last_name: String,
    val phone_number: String,
    val created_at: Date,
)
