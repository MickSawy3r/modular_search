package com.ticketswap.assessment.feature.auth.presenter

import com.spotify.sdk.android.auth.AuthorizationResponse
import com.ticketswap.platform.core.BaseViewModel
import com.ticketswap.platform.exception.Failure
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    fun loginUser(authResponse: AuthorizationResponse) {
        // Save Stuff
        when (authResponse.type) {
            AuthorizationResponse.Type.CODE -> {
            }
            else -> handleFailure(Failure.NetworkConnection)
        }
    }
}
