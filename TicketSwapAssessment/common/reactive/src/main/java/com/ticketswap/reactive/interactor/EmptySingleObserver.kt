package com.ticketswap.interactor

import io.reactivex.rxjava3.observers.DisposableSingleObserver

open class EmptySingleObserver<T> : DisposableSingleObserver<T>() {

    override fun onSuccess(result: T) {}

    override fun onError(throwable: Throwable) {}
}
