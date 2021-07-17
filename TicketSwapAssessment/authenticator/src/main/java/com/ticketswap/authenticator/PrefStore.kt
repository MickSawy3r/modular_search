package com.ticketswap.authenticator

import android.content.Context
import android.content.SharedPreferences
import java.util.*

internal class PrefStore constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("shared preferences", 0)

    fun setAuthToken(authToken: String) {
        sharedPreferences.edit()
            .putString(KEY_TOKEN, "Bearer $authToken")
            .apply()
    }

    fun getAuthToken(): String {
        return sharedPreferences.getString(KEY_TOKEN, "") ?: ""
    }

    fun removeAuthToken() {
        sharedPreferences.edit()
            .remove(KEY_TOKEN)
            .apply()
    }

    fun setExpiresIn(expiresIn: Int) {
        sharedPreferences.edit()
            .putInt(KEY_EXPIRES_IN, expiresIn)
            .apply()
    }

    fun getExpiresIn(): Int {
        return sharedPreferences
            .getInt(KEY_EXPIRES_IN, -1)
    }

    fun removeExpiresIn() {
        sharedPreferences.edit()
            .remove(KEY_EXPIRES_IN)
            .apply()
    }

    fun setLoggedInAt() {
        // Time in milliseconds
        val currentTime = (Calendar.getInstance().time.time * MILLISECONDS_IN_SECOND).toInt()
        sharedPreferences.edit()
            .putInt(KEY_ADDED_AT, currentTime)
            .apply()
    }

    fun removeLoggedInAt() {
        sharedPreferences.edit()
            .remove(KEY_ADDED_AT)
            .apply()
    }

    fun getLoggedInAt(): Int {
        return sharedPreferences
            .getInt(KEY_ADDED_AT, -1)
    }

    companion object {
        private const val KEY_TOKEN = "token"
        private const val KEY_EXPIRES_IN = "expires-in"
        private const val KEY_ADDED_AT = "added-at"
        private const val MILLISECONDS_IN_SECOND = 1000
    }
}
