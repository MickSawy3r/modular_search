package com.ticketswap.assessment.feature.auth.presenter

import androidx.lifecycle.MutableLiveData
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.ticketswap.assessment.feature.auth.domain.failures.LoginErrorFailure
import com.ticketswap.assessment.feature.auth.domain.usecase.LoginUserUseCase
import com.ticketswap.platform.core.BaseViewModel
import com.ticketswap.platform.exception.Failure
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : BaseViewModel() {

    private val _loggedInLiveData = MutableLiveData(false)
    val loggedInLiveData = _loggedInLiveData

    private val _loadingLiveData = MutableLiveData(false)
    val loadingLiveData = _loadingLiveData

    fun loginUser(authResponse: AuthorizationResponse) {
        _loadingLiveData.postValue(true)
        // Save Stuff
        when (authResponse.type) {
            AuthorizationResponse.Type.CODE -> {
                loginUserUseCase.execute(observer = LoginObserver())
            }
            else -> handleFailure(Failure.NetworkConnection)
        }
    }

    private inner class LoginObserver : DisposableSingleObserver<Boolean>() {
        override fun onSuccess(t: Boolean?) {
            _loadingLiveData.postValue(false)
            _loggedInLiveData.postValue(true)
        }

        override fun onError(e: Throwable?) {
            _loadingLiveData.postValue(false)
            handleFailure(LoginErrorFailure(e?.message))
        }
    }
}
