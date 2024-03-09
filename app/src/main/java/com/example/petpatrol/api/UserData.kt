package com.example.petpatrol.api

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("email_address") val email: String,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("phone_number") val phoneNumber: String?,
    @SerializedName("password") val password: String?
)
