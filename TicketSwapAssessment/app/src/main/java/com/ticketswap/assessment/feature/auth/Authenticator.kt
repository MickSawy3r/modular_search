package com.ticketswap.assessment.feature.auth

import com.ticketswap.assessment.core.platform.PrefStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator @Inject constructor(private val prefStore: PrefStore) {
    fun userLoggedIn() = prefStore.isLoggedIn()
}
