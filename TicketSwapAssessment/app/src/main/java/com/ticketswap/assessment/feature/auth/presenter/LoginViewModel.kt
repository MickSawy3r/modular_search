package com.ticketswap.assessment.feature.auth.presenter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.ticketswap.assessment.feature.auth.domain.failures.LoginErrorFailure
import com.ticketswap.assessment.feature.auth.domain.usecase.LoginUserUseCase
import com.ticketswap.platform.core.BaseViewModel
import com.ticketswap.extention.exception.Failure
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : BaseViewModel() {

    private val _loggedInLiveData = MutableLiveData(false)
    val loggedInLiveData = _loggedInLiveData

    fun loginUser(authResponse: AuthorizationResponse) {
        setLoading(true)
        // Save Stuff
        Log.d(TAG, "loginUser: ")
        when (authResponse.type) {
            AuthorizationResponse.Type.TOKEN -> {
                loginUserUseCase.execute(observer = LoginObserver(), authResponse)
            }
            AuthorizationResponse.Type.CODE -> {
                loginUserUseCase.execute(observer = LoginObserver(), authResponse)
            }
            else -> handleFailure(Failure.NetworkConnection)
        }
    }

    private inner class LoginObserver : DisposableSingleObserver<Boolean>() {
        override fun onSuccess(t: Boolean?) {
            Log.d(TAG, "onSuccess: Logged in")
            setLoading(false)
            _loggedInLiveData.postValue(true)
        }

        override fun onError(e: Throwable?) {
            Log.d(TAG, "onError: ${e?.message}")
            setLoading(false)
            handleFailure(LoginErrorFailure(e?.message))
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}
