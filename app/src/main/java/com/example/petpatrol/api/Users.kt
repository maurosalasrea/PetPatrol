package com.example.petpatrol.api

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: User?
) {
    data class User(
        @SerializedName("id") val user_id: Int
    )
}
