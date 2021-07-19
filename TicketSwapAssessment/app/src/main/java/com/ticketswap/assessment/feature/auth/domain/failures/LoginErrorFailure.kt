package com.ticketswap.assessment.feature.auth.domain.failures

import com.ticketswap.extention.Failure

class LoginErrorFailure constructor(val reason: String?) : Failure.FeatureFailure() {
    override fun toString(): String {
        return "Error Logging In: ${reason ?: ""}"
    }
}
