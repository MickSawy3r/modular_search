package com.ticketswap.assessment.feature.auth.domain.usecase

import com.spotify.sdk.android.auth.AuthorizationResponse
import com.ticketswap.assessment.feature.auth.domain.failures.LoginErrorFailure
import com.ticketswap.authenticator.AuthGuard
import com.ticketswap.reactive.executor.PostExecutionThread
import com.ticketswap.reactive.executor.ThreadExecutor
import com.ticketswap.reactive.interactor.SingleUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val authenticator: AuthGuard
) : SingleUseCase<Boolean, AuthorizationResponse>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseSingle(params: AuthorizationResponse?): Single<Boolean> {
        if (params == null) {
            return Single.error(LoginErrorFailure("Null Login Response!"))
        }
        authenticator.setLoggedIn(params.accessToken, params.expiresIn)
        return Single.just(true)
    }
}
