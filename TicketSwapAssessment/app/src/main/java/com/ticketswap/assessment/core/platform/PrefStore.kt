package com.ticketswap.assessment.core.platform

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefStore @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("shared preferences", 0)

    fun setAuthToken(authToken: String) {
        sharedPreferences.edit()
            .putString("token", "Bearer $authToken")
            .apply()
    }

    fun getAuthToken(): String {
        return sharedPreferences.getString("token", "") ?: ""
    }

    fun removeAuthToken() {
        sharedPreferences.edit()
            .remove("token")
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getString("token", "") != ""
    }
}
