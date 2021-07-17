package com.ticketswap.authenticator

import android.content.Context

class AuthGuard constructor(val context: Context) {
    fun userLoggedIn(): Boolean {
        val prefStore = PrefStore(context)
        return prefStore.isLoggedIn()
    }

    fun setLoggedIn(token: String) {
        return PrefStore(context).setAuthToken(token)
    }

    fun logout() {
        val prefStore = PrefStore(context)
        return prefStore.removeAuthToken()
    }
}
