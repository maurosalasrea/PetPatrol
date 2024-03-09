package com.example.petpatrol.api

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {
    private val PREFS_NAME = "com.example.petpatrol.prefs"
    private val USER_ID = "user_id"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var userId: Int
        get() = prefs.getInt(USER_ID, 0) // Retorna 0 si no se encuentra nada
        set(value) = prefs.edit().putInt(USER_ID, value).apply()
}
