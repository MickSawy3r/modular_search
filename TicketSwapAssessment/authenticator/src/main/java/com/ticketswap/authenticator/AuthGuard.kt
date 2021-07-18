package com.ticketswap.authenticator

import android.content.Context
import java.util.Calendar

class AuthGuard constructor(context: Context) {
    private var prefStore: PrefStore = PrefStore(context)

    fun userLoggedIn(): Boolean {
        val token = prefStore.getAuthToken()
        val expiresIn = prefStore.getExpiresIn()
        val addedTime = prefStore.getLoggedInAt()
        val currentTime = (Calendar.getInstance().time.time * MILLISECONDS_IN_SECOND).toInt()

        if (
            currentTime > addedTime + expiresIn &&
            token != "" &&
            // If both are negative then this one fails: -1 + -1 = -2
            // Written this way to avoid detekt warning
            expiresIn + addedTime > -1
        ) {
            return true
        }

        return false
    }

    fun setLoggedIn(accessToken: String, expiresIn: Int) {
        prefStore.setAuthToken(accessToken)
        prefStore.setExpiresIn(expiresIn)
        prefStore.setLoggedInAt()
    }

    fun logout() {
        prefStore.removeAuthToken()
        prefStore.removeExpiresIn()
        prefStore.removeLoggedInAt()
    }

    fun getAuthToken(): String = prefStore.getAuthToken()

    companion object {
        private const val MILLISECONDS_IN_SECOND = 1000
    }
}
